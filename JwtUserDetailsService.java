package com.sks.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("samiullakhan".equals(username)) {
			return new User("samiullakhan", "$2a$08$XAfaOxE0fUxZORjisVh9DuvUknpqigDxAMwAR9vvg/S2ZMKfHpctu",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException(username + " is not found in the records");
		}
	}
}
