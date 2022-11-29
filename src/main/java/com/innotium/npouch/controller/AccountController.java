package com.innotium.npouch.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innotium.npouch.annotation.LoginNotRequire;
import com.innotium.npouch.dto.AuthToken;
import com.innotium.npouch.dto.ListDataInfo;
import com.innotium.npouch.dto.ReqAccount;
import com.innotium.npouch.dto.ResAccount;
import com.innotium.npouch.dto.ResSuccess;
import com.innotium.npouch.enums.HeaderParameters;
import com.innotium.npouch.service.AccountService;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // 컨트롤러에서 설정
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@LoginNotRequire
	@RequestMapping(value = "/apim/account/login", method = RequestMethod.POST)
	public ResponseEntity<AuthToken> login(HttpServletRequest request, @RequestBody ReqAccount reqAccount) {
		String accountId = reqAccount.getAccountId();
		String password = reqAccount.getPassword();
		AuthToken authToken = accountService.getLoginAuth(request, accountId, password);
		return new ResponseEntity<>(authToken, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/apim/account/getAccountList", method = RequestMethod.GET)
	public ResponseEntity<ListDataInfo<ResAccount>> getAccountList(
			@RequestParam(name="startIndex", required=false, defaultValue="0") int startIndex,
			@RequestParam(name="pageSize", required=false, defaultValue="20") int pageSize,
			@RequestParam(name="searchOption", required=false, defaultValue="") String searchOption,
			@RequestParam(name="searchOptionText", required=false, defaultValue="") String searchOptionText,
			@RequestParam(name="statuses", required=false, defaultValue="") int[] statuses) {
		
		ListDataInfo<ResAccount> listDataInfo = accountService.getAccountList(startIndex, pageSize, searchOption, searchOptionText, statuses);
				
		return new ResponseEntity<>(listDataInfo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/account", method = RequestMethod.GET)
	public ResAccount getAccountInfo() {
		ResAccount resAccount = accountService.getAccountInfo();
		
		return resAccount;
	}
	
	@RequestMapping(value = "/apim/account", method = RequestMethod.GET)
	public ResAccount getSelectAccount(@RequestParam(name="accountIdx", required=true) int accountIdx) {
		ResAccount resAccount = accountService.getSelectAccount(accountIdx);
		
		return resAccount;
	}
	
	@RequestMapping(value = "/apim/account", method = RequestMethod.POST)
	public ResSuccess addAccount(HttpServletRequest request, @RequestBody ReqAccount reqAccount) {
		String accountId = reqAccount.getAccountId();
		String name = reqAccount.getName();
		String password = reqAccount.getPassword();
		String grade = reqAccount.getGrade();
		int groupId = reqAccount.getGroupId();
		int status = reqAccount.getStatus();
		
		accountService.addAccount(accountId, name, password, grade, groupId, status);
		
		return new ResSuccess();
	}
	
	@RequestMapping(value = "/apim/account", method = RequestMethod.PUT)
	public ResSuccess modifyAccount(@RequestBody ReqAccount reqAccount) {
		
		int accountIdx = ObjectUtils.defaultIfNull(reqAccount.getAccountIdx(), -1);
		String name = StringUtils.trim(reqAccount.getName());
		String password = StringUtils.trim(reqAccount.getPassword());
		String grade = StringUtils.trim(reqAccount.getGrade());
		int groupId = ObjectUtils.defaultIfNull(reqAccount.getGroupId(), -1);
		int status = ObjectUtils.defaultIfNull(reqAccount.getStatus(), -1);
		
		accountService.modifyAccount(accountIdx, name, password, grade, groupId, status);
		
		return new ResSuccess();
	}
	
	@RequestMapping(value = "/apim/account", method = RequestMethod.DELETE)
	public ResSuccess removeAccount(@RequestParam(name="accountIdxs", required=true) int[] accountIdxs) {
		accountService.removeAccount(accountIdxs);
		
		return new ResSuccess();
	}
	
	@RequestMapping(value = "/apim/account/getAccountListByGroupId", method = RequestMethod.GET)
	public ResponseEntity<ListDataInfo<ResAccount>> getAccountListByGroupId(@RequestParam(name="groupId", required=true) int groupId) {
		
		ListDataInfo<ResAccount> listDataInfo = accountService.getAccountListByGroupId(groupId);
		
		return new ResponseEntity<>(listDataInfo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/apim/account/modifyAccounts", method = RequestMethod.PUT)
	public void modifyAccounts(
			@RequestParam(name="groupId", required=false, defaultValue="-1") int groupId,
			@RequestParam(name="accountIdxs", required=true) int[] accountIdxs) {

		accountService.modifyAccounts(groupId, accountIdxs);

	}
	
}
