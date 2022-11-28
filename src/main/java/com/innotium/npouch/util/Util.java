package com.innotium.npouch.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class Util {

	/** blank 체크 */
	public static boolean isNotBlank(Object obj) {
		if(obj == null) {
			return false;
		}
		if(obj instanceof Number) {
			 Number num = (Number) obj;
			 return num.longValue() > -1;
		}

		return StringUtils.isNotBlank(Objects.toString(obj, ""));
	}
	
	/** date String 리턴 */
	public static String newDateString() {
		return dateToString(new Date());
	}
	
	/** date to String */
	public static String dateToString(Date dateTime) {
		return Util.dateToString(dateTime, "yyyy-MM-dd HH:mm:ss");
	}
	
	/** date to String */
	public static String dateToString(Date dateTime, String format) {
		if(dateTime != null) {
			SimpleDateFormat transFormat = new SimpleDateFormat(format);
			return transFormat.format(dateTime);
		}

		return null;
	}
	
	/** equal */
	public static boolean equals(String str1, String str2) {
		return StringUtils.equals(str1, str2);
	}
}
