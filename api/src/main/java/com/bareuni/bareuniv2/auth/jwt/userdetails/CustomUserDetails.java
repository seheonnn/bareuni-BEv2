package com.bareuni.bareuniv2.auth.jwt.userdetails;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {

	private final Long id;
	private final String email;
	private final String password;
	private final String authority;

	public CustomUserDetails(Long id, String email, String password, String authority) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.authority = authority;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();

		collection.add((GrantedAuthority)() -> authority);

		return collection;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	// 계정 만료되지 않음
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정 잠겨있지 않음
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호 변경 기간
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 1년 간 로그인 없을 시 "휴면"으로
	// (현재 시간 - loginDate) > 1년 -> return false; 로 설정
	// 활성화 되어있음
	@Override
	public boolean isEnabled() {
		// 사이트에서 1년 동안 회원이 로그인을 안하면 -> 휴면 계정으로 전환하는 로직이 있다고 치자
		// user entity 의 field 에 "Timestamp loginDate"를 하나 만들어주고
		// (현재 시간 - loginDate) > 1년 -> return false; 로 설정
		return true;
	}
}
