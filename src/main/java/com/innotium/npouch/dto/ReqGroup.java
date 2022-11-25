package com.innotium.npouch.dto;

import com.innotium.npouch.model.Group;

import lombok.Data;

@Data
public class ReqGroup {
	private int groupId;
	private String groupName;
	private String phoneNumber;
	private int parentGroupId;
}
