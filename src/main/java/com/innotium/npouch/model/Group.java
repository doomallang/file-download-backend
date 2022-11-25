package com.innotium.npouch.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Group {
	private int groupId;
	private String groupName;
	private String phoneNumber;
	private int parentGroupId;
	@JsonFormat
	(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date createDatetime;
	@JsonFormat
	(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date updateDatetime;
	private int updateId;
	private int createId;
}
