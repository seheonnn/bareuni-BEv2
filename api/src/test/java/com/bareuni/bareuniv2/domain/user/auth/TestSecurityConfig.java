package com.bareuni.bareuniv2.domain.user.auth;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.bareuni.bareuniv2.auth.config.SecurityConfig;
import com.bareuni.bareuniv2.auth.jwt.util.JwtUtil;

@TestConfiguration
public class TestSecurityConfig {

	@Bean
	public JwtUtil jwtUtil() {
		return Mockito.mock(JwtUtil.class);
	}

	@Bean
	public SecurityConfig securityConfig() {
		return Mockito.mock(SecurityConfig.class);
	}
}
