package com.innotium.npouch.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

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
import com.innotium.npouch.enums.HeaderParameters;
import com.innotium.npouch.service.AccountService;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // 컨트롤러에서 설정
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@LoginNotRequire
	@RequestMapping(value = "/api/account/login", method = RequestMethod.POST)
	public ResponseEntity<AuthToken> login(HttpServletRequest request, @RequestBody ReqAccount reqAccount) {
		String accountId = reqAccount.getAccountId();
		String password = reqAccount.getPassword();
		AuthToken authToken = accountService.getLoginAuth(request, accountId, password);
		return new ResponseEntity<>(authToken, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/account/getAccountList", method = RequestMethod.GET)
	public ResponseEntity<ListDataInfo<ResAccount>> getAccountList(
			@RequestParam(name="startIndex", required=false, defaultValue="0") int startIndex,
			@RequestParam(name="pageSize", required=false, defaultValue="20") int pageSize,
			@RequestParam(name="searchText", required=false, defaultValue="") String searchText,
			@RequestParam(name="searchTextOption", required=false, defaultValue="") String searchTextOption) {

		ListDataInfo<ResAccount> listDataInfo = accountService.getAccountList(startIndex, pageSize, searchText, searchTextOption);
				
		return new ResponseEntity<>(listDataInfo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/account/addAccount", method = RequestMethod.POST)
	public void addAccount(HttpServletRequest request, @RequestBody ReqAccount reqAccount) {
		String accountId = reqAccount.getAccountId();
		String password = reqAccount.getPassword();
		String name = reqAccount.getName();
		int groupId = reqAccount.getGroupId();
		
		accountService.addAccount(accountId, password, name, groupId);
	}
	
	@RequestMapping(value = "/api/account/getSelectAccount", method = RequestMethod.GET)
	public ResAccount getSelectAccount(@RequestParam(name="accountId", required=true) int accountId) {

		ResAccount resAccount = accountService.getSelectAccount(accountId);
		
		return resAccount;
	}
	
	@RequestMapping(value = "/api/account/getAccountListByGroupId", method = RequestMethod.GET)
	public ResponseEntity<ListDataInfo<ResAccount>> getAccountListByGroupId(@RequestParam(name="groupId", required=true) int groupId) {
		
		ListDataInfo<ResAccount> listDataInfo = accountService.getAccountListByGroupId(groupId);
		
		return new ResponseEntity<>(listDataInfo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/account/modifyAccounts", method = RequestMethod.PUT)
	public void modifyAccounts(
			@RequestParam(name="groupId", required=true) int groupId,
			@RequestParam(name="accountIdxs", required=true) int[] accountIdxs) {

		accountService.modifyAccounts(groupId, accountIdxs);

	}
	
}
