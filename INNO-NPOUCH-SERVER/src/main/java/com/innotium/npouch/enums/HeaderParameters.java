package com.innotium.npouch.enums;

public enum HeaderParameters {
	LIST_TOTAL_COUNT("Paging-total-count"),
	CLIENT_IP("Client-ip"),
	USER_ID("User-id"),
	SERVER_DATETIME("Server-datetime"),
	AUTH("X-auth");

	private String name;

	HeaderParameters(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
