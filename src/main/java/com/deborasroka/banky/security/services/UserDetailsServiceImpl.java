package com.deborasroka.banky.security.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deborasroka.banky.model.User;
import com.deborasroka.banky.service.UserService;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserService userServ;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userServ.findUserByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

		UserDetails detals = UserDetailsImpl.build(user);



		return detals;
	}

}
