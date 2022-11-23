package com.innotium.npouch.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserRole implements CodeEnum {
	MANAGER("MANAGER", 1, "관리자", "ENUM.ACCOUNT_ROLE.MANAGER"),
	GROUP("GROUP", 2, "부서 관리자", "ENUM.ACCOUNT_ROLE.GROUP"),
	USER("USER", 100, "사용자", "ENUM.ACCOUNT_ROLE.USER")
	;

	private String name;
	private int code;
	private String text;
	private String i18nText;
	
	UserRole(String name, int code, String text, String i18nText) {
		this.name = name;
		this.code = code;
		this.text = text;
		this.i18nText = i18nText;
	}
	
	@Override
	public int getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getI18nText() {
		// TODO Auto-generated method stub
		return i18nText;
	}
	
	@MappedTypes(UserRole.class)
	public static class AccountRoleTypeHandler extends EnumTypeHandler<UserRole> {
		public AccountRoleTypeHandler() {
			super(UserRole.class);
		}
	}

}
