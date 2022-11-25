package com.innotium.npouch.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.innotium.npouch.dto.AuthAccount;
import com.innotium.npouch.dto.DtoRole;
import com.innotium.npouch.dto.ResAccount;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	AccountService accountService;
	
	@Override
	public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
		ResAccount resAccount = accountService.selectAccountByAccountId(accountId);
		if(resAccount == null) {
			throw new UsernameNotFoundException("SERVER.MESSAGE.NO_DATA_MATCHES_THE_INFOMATION_YOU_ENTERED");
		}
		
		// test
		List<DtoRole> roleList = new ArrayList<>();
		DtoRole dtoRole = new DtoRole(0, "MANAGER", 0, null);
		roleList.add(dtoRole);
		
		return new AuthAccount(resAccount.getAccountIdx(), resAccount.getAccountId(), resAccount.getName(), resAccount.getPassword(), roleList);
	}
	
}
