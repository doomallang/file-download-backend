package com.innotium.npouch.dto;

import java.util.Date;

import com.innotium.npouch.model.Role;

import lombok.Builder;

public class DtoRole extends Role {
	
	public DtoRole() {
		
	}
	
	@Builder
	public DtoRole(int roleIdx, String roleName, int createAccountIdx, Date updateDatetime) {
		super.setRoleIdx(roleIdx);
		super.setRoleName(roleName);
		super.setCreateAccountIdx(createAccountIdx);
		super.setUpdateDatetime(updateDatetime);
	}
}
