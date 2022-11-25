package com.innotium.npouch.dto;

import com.innotium.npouch.model.Group;

import lombok.Builder;
import lombok.Data;

@Data
public class ResGroup extends Group{
	private int hasChild;
	private String parentGroupName;
}
