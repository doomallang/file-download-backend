package com.innotium.npouch.dto;

import java.util.List;

import lombok.Data;

@Data
public class ListDataInfo<T> {
	private List<T> list;
	private long listCount;

	public ListDataInfo(List<T> list, long listCount) {
		this.list = list;
		this.listCount = listCount;
	}
}
