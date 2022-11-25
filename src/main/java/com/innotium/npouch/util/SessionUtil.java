package com.innotium.npouch.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.innotium.npouch.dto.AuthAccount;
import com.innotium.npouch.exception.FailException;

@Component
public class SessionUtil {

	/**
	 * 세션 정보 리턴
	 * @return
	 */
	public static AuthAccount getSessionInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null) {
			throw new FailException("SERVER.MESSAGE.SESSION_INFOMATION_HAS_EXPIRED");
		}
		AuthAccount authAccount = (AuthAccount)authentication.getPrincipal();

		return authAccount;
	}

}
