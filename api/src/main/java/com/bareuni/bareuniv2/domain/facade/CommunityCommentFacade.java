package com.bareuni.bareuniv2.domain.facade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.bareuniv2.domain.comment.dto.CreateCommentRequest;
import com.bareuni.bareuniv2.domain.comment.dto.CreateCommentResponse;
import com.bareuni.bareuniv2.domain.comment.dto.GetCommentsResponse;
import com.bareuni.bareuniv2.domain.comment.service.CommentService;
import com.bareuni.bareuniv2.domain.community.dto.GetCommunitiesResponse;
import com.bareuni.bareuniv2.domain.community.service.CommunityQueryService;
import com.bareuni.bareuniv2.domain.page.PageCondition;
import com.bareuni.bareuniv2.domain.page.PageResponse;
import com.bareuni.bareuniv2.domain.user.dto.UserSummary;
import com.bareuni.coredomain.domain.comment.Comment;
import com.bareuni.coredomain.domain.community.Community;
import com.bareuni.coredomain.domain.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityCommentFacade {

	private final CommunityQueryService communityService;
	private final CommentService commentService;
	private final JdbcTemplate jdbcTemplate;

	public CreateCommentResponse createComment(Long communityId, User user, CreateCommentRequest request) {

		Community community = communityService.getCommunityById(communityId);

		Comment comment = request.toEntity();
		community.addComment(comment);
		comment.setUser(user);

		Comment savedComment = commentService.createComment(comment);
		return CreateCommentResponse.from(savedComment);
	}

	// Index + Window 함수
	@Transactional(readOnly = true)
	public PageResponse<GetCommunitiesResponse> getCommunitiesV3(PageCondition pageCondition) {
		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());

		// 1. 먼저 전체 커뮤니티 개수 구함
		Long total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM community", Long.class);

		// 2. 페이징에 해당하는 community.id 목록을 먼저 가져옴
		String idQuery = """
				SELECT cm.id
				FROM community cm
				ORDER BY cm.id DESC
				LIMIT ? OFFSET ?
			""";
		List<Long> pagedCommunityIds = jdbcTemplate.query(idQuery,
			(rs, rowNum) -> rs.getLong("id"),
			pageable.getPageSize(),
			pageable.getOffset()
		);

		if (pagedCommunityIds.isEmpty()) {
			return PageResponse.of(new PageImpl<>(List.of(), pageable, total));
		}

		// 3. 최신 댓글 3개까지 포함된 메인 쿼리
		String inClause = pagedCommunityIds.stream()
			.map(String::valueOf)
			.collect(Collectors.joining(",", "(", ")")); // <-- 괄호 포함!

		String sql = """
				SELECT * FROM (
					SELECT
						cm.id AS community_id,
						cm.title AS community_title,
						cm.content AS community_content,
						
						author.id AS author_id,
						author.username AS author_username,
						author_image.url AS author_profile_url,
						
						c.id AS comment_id,
						c.content AS comment_content,
						comment_user.id AS comment_user_id,
						comment_user.username AS comment_user_username,
						comment_user_image.url AS comment_user_profile_url,
						
						cnt.comment_cnt,
						ROW_NUMBER() OVER (PARTITION BY cm.id ORDER BY c.created_at DESC) AS rn
						
					FROM community cm
					JOIN app_user author ON cm.user_id = author.id
					LEFT JOIN user_image author_image ON author.user_image_id = author_image.id
						
					LEFT JOIN comment c ON c.community_id = cm.id
					LEFT JOIN app_user comment_user ON c.user_id = comment_user.id
					LEFT JOIN user_image comment_user_image ON comment_user.user_image_id = comment_user_image.id
						
					LEFT JOIN (
						SELECT community_id, COUNT(*) AS comment_cnt
						FROM comment
						GROUP BY community_id
					) cnt ON cm.id = cnt.community_id
						
					WHERE cm.id IN %s
				) sub
				WHERE sub.rn <= 3 OR sub.rn IS NULL
				ORDER BY sub.community_id DESC, sub.rn
			""".formatted(inClause);

		List<FlatCommunityRow> rows = jdbcTemplate.query(sql, new FlatCommunityRowMapper());

		List<GetCommunitiesResponse> result = rows.stream()
			.collect(Collectors.groupingBy(FlatCommunityRow::communityId, LinkedHashMap::new, Collectors.toList()))
			.entrySet()
			.stream()
			.map(entry -> {
				List<FlatCommunityRow> group = entry.getValue();
				FlatCommunityRow first = group.get(0);
				List<GetCommentsResponse> comments = group.stream()
					.filter(r -> r.commentId() != null)
					.map(r -> new GetCommentsResponse(
						r.commentId(),
						r.commentContent(),
						new UserSummary(r.commentUserUsername(), r.commentUserProfileUrl())
					))
					.toList();

				return GetCommunitiesResponse.builder()
					.id(first.communityId())
					.title(first.communityTitle())
					.content(first.communityContent())
					.user(new UserSummary(first.authorUsername(), first.authorProfileUrl()))
					.comments(comments)
					.commentCnt(first.commentCnt() == null ? 0 : first.commentCnt())
					.build();
			})
			.toList();

		return PageResponse.of(new PageImpl<>(result, pageable, total));
	}

	record FlatCommunityRow(
		Long communityId,
		String communityTitle,
		String communityContent,

		Long authorId,
		String authorUsername,
		String authorProfileUrl,

		Long commentId,
		String commentContent,
		Long commentUserId,
		String commentUserUsername,
		String commentUserProfileUrl,

		Integer commentCnt
	) {

	}

	static class FlatCommunityRowMapper implements RowMapper<FlatCommunityRow> {
		@Override
		public FlatCommunityRow mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new FlatCommunityRow(
				rs.getLong("community_id"),
				rs.getString("community_title"),
				rs.getString("community_content"),

				rs.getLong("author_id"),
				rs.getString("author_username"),
				rs.getString("author_profile_url"),

				rs.getObject("comment_id") == null ? null : rs.getLong("comment_id"),
				rs.getString("comment_content"),
				rs.getObject("comment_user_id") == null ? null : rs.getLong("comment_user_id"),
				rs.getString("comment_user_username"),
				rs.getString("comment_user_profile_url"),

				rs.getObject("comment_cnt") == null ? 0 : rs.getInt("comment_cnt")
			);
		}
	}
}
