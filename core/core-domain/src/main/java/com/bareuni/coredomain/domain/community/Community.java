package com.bareuni.coredomain.domain.community;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import com.bareuni.coredomain.domain.comment.Comment;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.global.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "community")
public class Community extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String tile;

	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	@Builder.Default
	@BatchSize(size = 1000)
	@OneToMany(mappedBy = "community", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<CommunityImage> communityImages = new ArrayList<>();

	@Builder.Default
	@BatchSize(size = 1000)
	@OneToMany(mappedBy = "community", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	public void setUser(User user) {
		this.user = user;
	}

	public void addCommunityImage(CommunityImage communityImage) {
		// if (this.communityImages == null) {
		// 	this.communityImages = new ArrayList<>();
		// }
		this.communityImages.add(communityImage);
		communityImage.setCommunity(this);
	}

	public void addComment(Comment comment) {
		this.comments.add(comment);
		comment.setCommunity(this);
	}

	public void update(String tile, String content) {
		this.tile = tile == null ? this.tile : tile;
		this.content = content == null ? this.content : content;
	}

	public int getCommentCount() {
		return comments.size();
	}
}
