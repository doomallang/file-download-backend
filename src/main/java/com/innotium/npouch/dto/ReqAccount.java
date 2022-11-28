package com.innotium.npouch.dto;

import com.innotium.npouch.model.Account;

import lombok.Builder;
import lombok.Data;

@Data
public class ReqAccount {
	private Integer accountIdx;
	private String accountId;
	private String password;
	private String name;
	private Integer groupId;
	private Integer status;
	private String grade;
}
