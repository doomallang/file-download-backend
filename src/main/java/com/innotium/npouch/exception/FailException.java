package com.innotium.npouch.exception;

import org.springframework.http.HttpStatus;

import com.innotium.npouch.exception.base.BaseException;

public class FailException extends BaseException {
	public FailException(final String message) {
		super(message);
	}

	public FailException(final String message, final int code) {
		super(message, code);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.UNPROCESSABLE_ENTITY;
	}
}
