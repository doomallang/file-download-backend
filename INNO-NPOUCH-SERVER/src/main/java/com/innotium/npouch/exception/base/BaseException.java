package com.innotium.npouch.exception.base;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
	public BaseException(final String message, final Throwable cause) {
        super(message, cause);
    }
	
    public BaseException(final String message) {
        super(message);
    }

    public BaseException(final Throwable cause) {
        super(cause);
    }

    public BaseException(final String message, final int code) {
		this(convertJSONString(message, code));
	}
    
    public static String convertJSONString(String message, int code) {
		JSONObject object = new JSONObject();
		try {
			object.put("message", message);
			object.put("code", code);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return object.toString();
	}
    
    public abstract HttpStatus getHttpStatus();
    
    public String getCode() {
		String className = this.getClass().getSimpleName();
		return className.substring(0, className.length() - 9);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}