package com.bareuni.bareuniv2.domain.queue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;

import com.bareuni.bareuniv2.domain.queue.service.QueueService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class WaitingRoomController {

	private final QueueService queueService;

	@GetMapping("/waiting-room")
	Mono<Rendering> waitingRoomPage(@RequestParam(name = "queue", defaultValue = "default") String queue,
		@RequestParam(name = "user_id") Long userId) {
		// 대기 등록
		// 웹페이지에 필요한 데이터를 전달
		return queueService.registerWaitQueue(queue, userId)
			.onErrorResume(ex -> queueService.getRank(queue, userId)) // 이미 등록되어 있는 경우, 대기 번호를 보여줌
			.map(rank -> Rendering.view("waiting-room")
				.modelAttribute("number", rank) // 웹페이지에 필요한 데이터를 전달
				.modelAttribute("userId", userId)
				.modelAttribute("queue", queue)
				.build());
	}
}
