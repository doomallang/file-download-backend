package com.innotium.npouch.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.innotium.npouch.dto.AuthAccount;
import com.innotium.npouch.dto.AuthToken;
import com.innotium.npouch.dto.ListDataInfo;
import com.innotium.npouch.dto.ReqAccount;
import com.innotium.npouch.dto.ResAccount;
import com.innotium.npouch.exception.FailException;
import com.innotium.npouch.repository.AccountRepository;
import com.innotium.npouch.util.SessionUtil;
import com.innotium.npouch.util.Util;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public AuthToken getLoginAuth(HttpServletRequest request, String accountId, String password) {
		UsernamePasswordAuthenticationToken token = null;
		Authentication authentication = null;
		int isAccountExist = accountService.isAccountExist(accountId);
		String sessionToken = "";
		System.out.println("gggggggggg");
		// 계정 유무 확인
		if (isAccountExist < 1) {
			throw new FailException("SERVER.MESSAGE.ID_DOES_NOT_EXISTS");
		}
		
		// 아이디 패스워드 로그인
		token = new UsernamePasswordAuthenticationToken(accountId, password);
		authentication = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		AuthAccount authAccount = (AuthAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HttpSession session = request.getSession(true);
		sessionToken = session.getId();
		
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
		session.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, authAccount.getUsername());

		AuthToken authToken = new AuthToken(authAccount.getAccountIdx(), authAccount.getUsername(), sessionToken, authAccount.getAuthorities());
		return authToken;
	}
	
	public ResAccount getAccountInfo() {
		AuthAccount authAccount = SessionUtil.getSessionInfo();
		int accountIdx = authAccount.getAccountIdx();
		
		ResAccount resAccount = accountService.selectAccount(accountIdx);
		
		return resAccount;
	}
	
	public ListDataInfo<ResAccount> getAccountList(int startIndex, int pageSize, String searchOption, String searchOptionText, int[] statuses) {
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("startIndex", startIndex);
		paramMap.put("pageSize", pageSize);
		paramMap.put("searchOption", searchOption);
		paramMap.put("searchOptionText", searchOptionText);
		paramMap.put("statuses", statuses);
		System.out.println(paramMap);
		int totalCount = accountRepository.selectAccountListCount(paramMap);
		List<ResAccount> list = accountRepository.selectAccountList(paramMap);
		
		return new ListDataInfo<ResAccount>(list, totalCount);
	}
	
	public ListDataInfo<ResAccount> getAccountListByGroupId(int groupId) {
		int totalCount = accountRepository.selectAccountListByGroupIdCount(groupId);
		List<ResAccount> list = accountRepository.selectAccountListByGroupId(groupId);
		
		return new ListDataInfo<ResAccount>(list, totalCount);
	}
	
	@Transactional
	public void addAccount(String accountId, String name, String password, String grade, int groupId, int status) {
		// 아이디 중복 여부
		if(accountRepository.selectAccountIdExists(accountId) > 0) {
			// 이미 등록된 아이디 입니다.
			throw new FailException("SERVER.MESSAGE.ALREADY_REGISTERED_ID");
		}
		
		AuthAccount authAccount = SessionUtil.getSessionInfo();
		int registerId = authAccount.getAccountIdx();
		Map<String, Object> paramMap = accountService.setAccountParamMap(0, accountId, name, password, grade, groupId, status, registerId);

		paramMap.put("updateDatetime", Util.newDateString());
		
		accountService.insertAccount(paramMap);
	}
	
	@Transactional
	public void modifyAccount(int accountIdx, String name, String password, String grade, int groupId, int status) {
		AuthAccount authAccount = SessionUtil.getSessionInfo();
		int registerId = authAccount.getAccountIdx();
		Map<String, Object> paramMap = accountService.setAccountParamMap(accountIdx, null, name, password, grade, groupId, status, registerId);
		System.out.println(paramMap);
		paramMap.put("updateDatetime", Util.newDateString());
		
		accountService.updateAccount(paramMap);
	}
	
	public ResAccount getSelectAccount(int accountIdx) {
		ResAccount resAccount = accountService.selectAccount(accountIdx);
		
		return resAccount;
	}
	
	@Transactional
	public void modifyAccounts(int groupId, int[] accountIdxs) {
		AuthAccount authAccount = SessionUtil.getSessionInfo();
		int registerId = authAccount.getAccountIdx();
		accountRepository.updateAccounts(groupId, accountIdxs, registerId, Util.newDateString());
	}
	
	@Transactional
	public void removeAccount(int[] accountIdxs) {
		accountRepository.deleteAccount(accountIdxs);
	}
	
	public Map<String, Object> setAccountParamMap(int accountIdx, String accountId, String name, String password, String grade, int groupId, int status, int registerId) {
		Map<String, Object> paramMap = Maps.newHashMap();
		if(accountIdx == 0) {
			paramMap.put("accountIdx", null);
		} else {
			paramMap.put("accountIdx", accountIdx);
		}

		paramMap.put("accountId", accountId);
		paramMap.put("name", name);
		paramMap.put("password", password);
		paramMap.put("grade", grade);
		paramMap.put("groupId", groupId);
		paramMap.put("status", status);
		paramMap.put("updateId", registerId);

		return paramMap;
	}
	
	public void insertAccount(Map<String, Object> paramMap) {
		accountRepository.insertAccount(paramMap);
	}
	
	public void updateAccount(Map<String, Object> paramMap) {
		accountRepository.updateAccount(paramMap);
	}
	
	public int isAccountExist(String accountId) {
		return accountRepository.isAccountExist(accountId);
	}
	
	public ResAccount selectAccountByAccountId(String accountId) {
		return accountRepository.selectAccountByAccountId(accountId);
	}
	
	public ResAccount selectAccount(int accountIdx) {
		return accountRepository.selectAccount(accountIdx);
	}
}
