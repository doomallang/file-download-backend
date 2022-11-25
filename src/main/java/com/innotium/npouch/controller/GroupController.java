package com.innotium.npouch.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innotium.npouch.dto.ReqGroup;
import com.innotium.npouch.dto.ResGroup;
import com.innotium.npouch.service.GroupService;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // 컨트롤러에서 설정
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = "/api/group/getTopGroupList", method = RequestMethod.GET)
	public ResponseEntity<List<ResGroup>> getTopGroupList(HttpServletRequest request) {
		List<ResGroup> dtoGroupList = groupService.getTopGroupList();
		
		return new ResponseEntity<>(dtoGroupList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/group/getSelectGroupList", method = RequestMethod.GET)
	public ResponseEntity<List<ResGroup>> getSelectGroupList(HttpServletRequest request,
			@RequestParam(name="groupId", required=true) int groupId,
			@RequestParam(name="exceptGroupId", required=false, defaultValue="-1") int exceptGroupId) {
		List<ResGroup> resGroupList = groupService.getSelectGroupList(groupId, exceptGroupId);
		
		return new ResponseEntity<>(resGroupList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/group", method = RequestMethod.POST)
	public void addGroup(HttpServletRequest request,
			@RequestBody ReqGroup reqGroup) {
		String groupName = reqGroup.getGroupName();
		String phoneNumber = reqGroup.getPhoneNumber();
		int parentGroupId = reqGroup.getParentGroupId();

		groupService.addGroup(groupName, phoneNumber, parentGroupId);
	}
	
	@RequestMapping(value = "/api/group", method = RequestMethod.GET)
	public ResponseEntity<ResGroup> addGroup(HttpServletRequest request,
			@RequestParam(name="groupId", required=true) int groupId) {

		ResGroup resGroup = groupService.getGroup(groupId);
		
		return new ResponseEntity<>(resGroup, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/api/group", method = RequestMethod.PUT)
	public void modifyGroup(HttpServletRequest request,
			@RequestBody ReqGroup reqGroup) {
		
		int groupId = reqGroup.getGroupId();
		String groupName = StringUtils.trim(reqGroup.getGroupName());
		String phoneNumber = StringUtils.trim(reqGroup.getPhoneNumber());
		int parentGroupId = reqGroup.getParentGroupId();
		
		groupService.modifyGroup(groupId, groupName, phoneNumber, parentGroupId);
	}
	
	@RequestMapping(value = "/api/group", method = RequestMethod.DELETE)
	public void removeGroup(@RequestParam(name="groupId", required=true) int groupId) {

		groupService.removeGroup(groupId);
	}
}
