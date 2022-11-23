package com.innotium.npouch.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.innotium.npouch.dto.ResGroup;

@Mapper
public interface GroupRepository {
	public List<ResGroup> selectTopGroupList();
	
	public List<ResGroup> selectGroupList(@Param("groupId") int groupId);
	
	public void insertGroup(Map<String, Object> paramMap);
	
	public void updateGroup(Map<String, Object> paramMap);
	
	public void deleteGroup(@Param("groupId") int groupId);
	
	public ResGroup selectGroup(@Param("groupId") int groupId);
}
