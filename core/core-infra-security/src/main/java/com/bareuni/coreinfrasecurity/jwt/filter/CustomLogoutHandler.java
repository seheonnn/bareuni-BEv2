package com.bareuni.coreinfrasecurity.jwt.filter;

import java.util.concurrent.TimeUnit;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.bareuni.coreinfraredis.util.RedisUtil;
import com.bareuni.coreinfrasecurity.jwt.exception.SecurityCustomException;
import com.bareuni.coreinfrasecurity.jwt.exception.SecurityErrorCode;
import com.bareuni.coreinfrasecurity.jwt.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

	private final RedisUtil redisUtil;
	private final JwtUtil jwtUtil;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		try {
			log.info("[*] Logout Filter");

			String accessToken = jwtUtil.resolveAccessToken(request);

			redisUtil.saveAsValue(
				accessToken,
				"logout",
				jwtUtil.getExpTime(accessToken),
				TimeUnit.MILLISECONDS
			);

			String email = jwtUtil.getUsername(accessToken);
			// String email = jwtUtil.getEmail(accessToken);

			redisUtil.delete(
				email + "_refresh_token"
			);

		} catch (ExpiredJwtException e) {
			log.warn("[*] case : accessToken expired");
			throw new SecurityCustomException(SecurityErrorCode.TOKEN_EXPIRED);
		}
	}
}
