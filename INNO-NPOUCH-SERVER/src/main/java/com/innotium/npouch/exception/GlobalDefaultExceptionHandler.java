package com.innotium.npouch.exception;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.innotium.npouch.dto.ResErrorMessage;
import com.innotium.npouch.exception.base.BaseException;

@ControllerAdvice
class GlobalDefaultExceptionHandler {
	@ExceptionHandler({BaseException.class})
	public ResponseEntity<ResErrorMessage> handleExceptionInternal(Exception e, HttpServletRequest req) {
		boolean isFailException = e.getClass().equals(FailException.class) ? true : false;
		BaseException baseException = (BaseException)e;
		HttpStatus status = baseException.getHttpStatus();
		HttpHeaders headers = new HttpHeaders();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		ResErrorMessage dto = ResErrorMessage.create(
			timestamp.getTime(), status.value(), status.getReasonPhrase(), status.getReasonPhrase(),
			baseException.getMessage(), req.getRequestURI(), isFailException
		);

		return new ResponseEntity<>(dto, headers, status);
	}
}
