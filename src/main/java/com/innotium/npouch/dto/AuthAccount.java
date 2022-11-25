package com.innotium.npouch.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.Lists;

public class AuthAccount implements UserDetails {
	private int accountIdx;
	private String accountId;
	private String name;
	private String password;
	private boolean enabled = true;
	private List<DtoRole> accountRoleList = Lists.newArrayList();
	private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	
	public AuthAccount(int accountIdx, String accountId, String name, String password, List<DtoRole> accountRole) {
		this.accountIdx = accountIdx;
		this.accountId = accountId;
		this.name = name;
		this.password = password;
		// export them as part of authorities
		for (DtoRole r : accountRole) {
			authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
		}
	}
	
	public int getAccountIdx() {
		return this.accountIdx;
	}
	
	public String getAccountId() {
		return this.accountId;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public String getUsername() {
		return Objects.toString(this.accountIdx);
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

}
