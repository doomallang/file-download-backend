package com.innotium.npouch.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Account {
	private int accountIdx;
	private String accountId;
	private String password;
	private String name;
	private int groupId;
	private int status;
	private String grade;
	@JsonFormat
	(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date createDatetime;
	@JsonFormat
	(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date updateDatetime;
	@JsonFormat
	(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date passwordChangeDatetime;
}
