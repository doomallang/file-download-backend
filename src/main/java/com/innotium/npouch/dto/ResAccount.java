package com.innotium.npouch.dto;

import java.util.List;

import com.innotium.npouch.model.Account;

import lombok.Builder;
import lombok.Data;

@Data
public class ResAccount extends Account {
	private String groupName;
}
