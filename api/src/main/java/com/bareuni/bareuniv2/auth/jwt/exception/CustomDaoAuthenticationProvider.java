package com.bareuni.bareuniv2.auth.jwt.exception;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

	public CustomDaoAuthenticationProvider() {
		this.setHideUserNotFoundExceptions(false);
	}
}
