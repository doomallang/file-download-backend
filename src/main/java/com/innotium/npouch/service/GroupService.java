package com.innotium.npouch.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.innotium.npouch.dto.AuthAccount;
import com.innotium.npouch.dto.ResGroup;
import com.innotium.npouch.exception.FailException;
import com.innotium.npouch.repository.GroupRepository;
import com.innotium.npouch.util.SessionUtil;
import com.innotium.npouch.util.Util;

@Service
public class GroupService {
	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupRepository groupRepository;

	public List<ResGroup> getTopGroupList() {
		List<ResGroup> dtoGroupList = groupRepository.selectTopGroupList();

		return dtoGroupList;
	}

	public List<ResGroup> getSelectGroupList(int groupId, int exceptGroupId) {
		
		List<ResGroup> resGroupList = groupRepository.selectGroupList(groupId, exceptGroupId);

		return resGroupList;
	}

	@Transactional
	public void addGroup(String groupName, String phoneNumber, int parentGroupId) {
		AuthAccount authAccount = SessionUtil.getSessionInfo();
		int registerId = authAccount.getAccountIdx();
		Map<String, Object> paramMap = groupService.setGroupParamMap(0, groupName, phoneNumber, parentGroupId, registerId);

		paramMap.put("updateDatetime", Util.newDateString());

		groupRepository.insertGroup(paramMap);
	}
	
	@Transactional
	public void modifyGroup(int groupId, String groupName, String phoneNumber, int parentGroupId) {
		AuthAccount authAccount = SessionUtil.getSessionInfo();
		int registerId = authAccount.getAccountIdx();
		Map<String, Object> paramMap = groupService.setGroupParamMap(groupId, groupName, phoneNumber, parentGroupId, registerId);
		
		paramMap.put("updateDatetime", Util.newDateString());
		List<ResGroup> list = groupService.selectParentGroupList(parentGroupId);
		
		// 이동할 그룹이 내 하위그룹일 경우 ERROR
		groupId = 1;
		for(ResGroup resGroup : list) {
			if(groupId == resGroup.getGroupId()) {
				System.out.println("SERVER.MESSAGE.CANNOT_MOVE_MY_SUB_GROUP");
				throw new FailException("SERVER.MESSAGE.CANNOT_MOVE_MY_SUB_GROUP");
			}
		}
		
		groupRepository.updateGroup(paramMap);
	}
	
	@Transactional
	public void removeGroup(int groupId) {
		groupRepository.deleteGroup(groupId);
	}
	
	@Transactional
	public ResGroup getGroup(int groupId) {
		ResGroup resGroup = groupRepository.selectGroup(groupId);
		
		return resGroup;
	}

	public Map<String, Object> setGroupParamMap(int groupId, String groupName, String phoneNumber, int parentGroupId, int registerId) {
		Map<String, Object> paramMap = Maps.newHashMap();
		if(groupId == 0) {
			paramMap.put("groupId", null);
		} else {
			paramMap.put("groupId", groupId);
		}

		paramMap.put("groupName", groupName);
		paramMap.put("phoneNumber", phoneNumber);
		paramMap.put("parentGroupId", parentGroupId);
		paramMap.put("updateId", registerId);

		return paramMap;
	}
	
	public List<ResGroup> selectParentGroupList(int groupId) {
		List<ResGroup> list = groupRepository.selectParentGroupList(groupId);
				
		return list;
	}
}
