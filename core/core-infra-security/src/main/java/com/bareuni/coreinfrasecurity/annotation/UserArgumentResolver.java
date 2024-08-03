package com.bareuni.coreinfrasecurity.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bareuni.coredomain.domain.user.entity.User;
import com.bareuni.coredomain.domain.user.repository.UserRepository;
import com.bareuni.coreinfrasecurity.jwt.exception.SecurityCustomException;
import com.bareuni.coreinfrasecurity.jwt.exception.SecurityErrorCode;
import com.bareuni.coreinfrasecurity.jwt.userdetails.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	private final UserRepository userRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasParameterAnnotation = parameter.hasParameterAnnotation(UserResolver.class);
		boolean isOrganizationParameterType = parameter.getParameterType().isAssignableFrom(User.class);
		return hasParameterAnnotation && isOrganizationParameterType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {
			return userRepository.findById(((CustomUserDetails)userDetails).getId())
				.orElseThrow(() -> new SecurityCustomException(SecurityErrorCode.TOKEN_NOT_FOUND));
		} catch (ClassCastException e) {
			// 로그아웃된 토큰
			throw new SecurityCustomException(SecurityErrorCode.UNAUTHORIZED);
		}
	}
}
