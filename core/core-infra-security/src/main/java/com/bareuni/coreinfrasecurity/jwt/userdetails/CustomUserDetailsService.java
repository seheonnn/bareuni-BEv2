package com.bareuni.coreinfrasecurity.jwt.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bareuni.coredomain.domain.user.entity.User;
import com.bareuni.coredomain.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		final User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("Account not found"));

		log.info("[*] User found : " + user.getUsername());

		return new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword(), user.getRole().toString());
	}
}
