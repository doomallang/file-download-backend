package com.innotium.npouch.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.Data;

@Data
public class ResSuccess {

	
	private int statusCode = HttpStatus.OK.value();
	
	private String message = "success";
	
	private ObjectNode result;

	public ResSuccess() {
	}
	public ResSuccess(ObjectNode result) {
		this.setResult(result);
	}
}
