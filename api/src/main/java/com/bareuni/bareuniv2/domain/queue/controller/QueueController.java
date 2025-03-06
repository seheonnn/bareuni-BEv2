package com.bareuni.bareuniv2.domain.queue.controller;

import java.time.Duration;

import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.bareuni.bareuniv2.domain.queue.dto.AllowUserResponse;
import com.bareuni.bareuniv2.domain.queue.dto.AllowedUserResponse;
import com.bareuni.bareuniv2.domain.queue.dto.QueueResponse;
import com.bareuni.bareuniv2.domain.queue.dto.RankNumberResponse;
import com.bareuni.bareuniv2.domain.queue.service.QueueService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/api/v2/queue")
@RestController
public class QueueController {

	private final QueueService queueService;

	// 대기 큐 등록 api
	@PostMapping("")
	public Mono<QueueResponse> registerUser(
		@RequestParam(name = "queue", defaultValue = "default") String queue,
		@RequestParam(name = "user_id") Long userId
	) {
		return queueService.registerWaitQueue(queue, userId)
			.map(QueueResponse::new);
	}

	// 진입을 허용하는 api
	@PostMapping("/allow")
	public Mono<AllowUserResponse> allowUser(@RequestParam(name = "queue", defaultValue = "default") String queue,
		@RequestParam(name = "count") Long count) {
		return queueService.allowUser(queue, count)
			.map(allowed -> new AllowUserResponse(count, allowed));
	}

	// 진입이 가능한 상태인지 조회하는 api
	@GetMapping("/allowed")
	public Mono<AllowedUserResponse> isAllowedUser(@RequestParam(name = "queue", defaultValue = "default") String queue,
		@RequestParam(name = "user_id") Long userId) {
		return queueService.isAllowed(queue, userId)
			.map(AllowedUserResponse::new);
	}

	// 대기 순번 조회 api
	@GetMapping("/rank")
	public Mono<RankNumberResponse> getRankUser(@RequestParam(name = "queue", defaultValue = "default") String queue,
		@RequestParam(name = "user_id") Long userId) {
		return queueService.getRank(queue, userId)
			.map(RankNumberResponse::new);
	}

	// 대기열 이탈
	@GetMapping("/touch")
	Mono<?> touch(@RequestParam(name = "queue", defaultValue = "default") String queue,
		@RequestParam(name = "user_id") Long userId,
		ServerWebExchange exchange) {

		return Mono.defer(() -> queueService.generateToken(queue, userId))
			.map(token -> {
				exchange.getResponse().addCookie(
					ResponseCookie.from("user-queue-%s-token".formatted(queue), token)
						.maxAge(Duration.ofSeconds(300)) // 5분
						.path("/")
						.build()
				);
				return token;
			});
	}
}
