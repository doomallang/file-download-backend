package com.innotium.npouch.dto;

import java.util.Collection;
import lombok.Builder;
import lombok.Data;

@Data
public class AuthToken {
	private int accountIdx;
	private String accountId;
	private String token;
	private Collection<?> authorities;
	
	public AuthToken() {
		
	}
	
	@Builder
	public AuthToken(int accountIdx, String accountId, String token, Collection<?> authorities) {
		this.setAccountIdx(accountIdx);
		this.setAccountId(accountId);
		this.setToken(token);
		this.setAuthorities(authorities);
	}
}
