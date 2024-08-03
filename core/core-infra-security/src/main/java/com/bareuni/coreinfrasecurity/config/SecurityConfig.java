package com.bareuni.coreinfrasecurity.config;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.bareuni.coreinfraredis.util.RedisUtil;
import com.bareuni.coreinfrasecurity.jwt.exception.JwtAccessDeniedHandler;
import com.bareuni.coreinfrasecurity.jwt.exception.JwtAuthenticationEntryPoint;
import com.bareuni.coreinfrasecurity.jwt.filter.CustomLoginFilter;
import com.bareuni.coreinfrasecurity.jwt.filter.CustomLogoutHandler;
import com.bareuni.coreinfrasecurity.jwt.filter.JwtAuthenticationFilter;
import com.bareuni.coreinfrasecurity.jwt.filter.JwtExceptionFilter;
import com.bareuni.coreinfrasecurity.jwt.util.HttpResponseUtil;
import com.bareuni.coreinfrasecurity.jwt.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final String[] swaggerUrls = {"/swagger-ui/**", "/v3/**"};
	private final String[] authUrls = {"/", "/api/v2/auth/join/**", "/api/v2/auth/login/**",
		"/health", "/actuator/**"};
	private final String[] allowedUrls = Stream.concat(Arrays.stream(swaggerUrls), Arrays.stream(authUrls))
		.toArray(String[]::new);

	private final AuthenticationConfiguration authenticationConfiguration;

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final UserDetailsService userDetailsService;
	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// CorsConfig 적용
		http
			.cors(cors -> cors
				.configurationSource(CorsConfig.apiConfigurationSource()));

		// csrf disable
		http
			.csrf(AbstractHttpConfigurer::disable);

		// form 로그인 방식 disable
		http
			.formLogin(AbstractHttpConfigurer::disable);

		// http basic 인증 방식 disable
		http
			.httpBasic(AbstractHttpConfigurer::disable);

		// session 사용 x, Stateless 서버를 만듦
		http
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);

		// 경로별 인가 작업
		http
			.authorizeHttpRequests(authorizeRequests -> authorizeRequests
				.requestMatchers("/user/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGE")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers(allowedUrls).permitAll()
				.anyRequest().permitAll()
			);

		// Jwt Filter (with login)
		CustomLoginFilter loginFilter = new CustomLoginFilter(
			authenticationManager(authenticationConfiguration), jwtUtil
		);
		loginFilter.setFilterProcessesUrl("/api/v2/auth/login");

		http
			.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

		// JwtExceptionFilter 사용
		http
			.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, redisUtil), CustomLoginFilter.class);

		http
			.addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);

		http
			.exceptionHandling(exception -> exception
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
			);

		// Logout Filter
		http
			.logout(logout -> logout
				.logoutUrl("/api/v2/auth/logout")
				.addLogoutHandler(new CustomLogoutHandler(redisUtil, jwtUtil))
				.logoutSuccessHandler((request, response, authentication) ->
					HttpResponseUtil.setSuccessResponse(
						response,
						HttpStatus.OK,
						"로그아웃 성공"
					)
				)
			)
			.addFilterAfter(new LogoutFilter(
					(request, response, authentication) ->
						HttpResponseUtil.setSuccessResponse(
							response,
							HttpStatus.OK,
							"로그아웃 성공"
						), new CustomLogoutHandler(redisUtil, jwtUtil)),
				JwtAuthenticationFilter.class
			);

		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setHideUserNotFoundExceptions(false);
		return authenticationProvider;
	}
}
