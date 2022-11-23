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
	
	public ListDataInfo<ResAccount> getAccountList(int startIndex, int pageSize, String searchText, String searchTextOption) {
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("startIndex", startIndex);
		paramMap.put("pageSize", pageSize);
		paramMap.put("searchText", searchText);
		paramMap.put("searchTextOption", searchTextOption);
		
		int totalCount = accountRepository.selectAccountListCount(paramMap);
		List<ResAccount> list = accountRepository.selectAccountList(paramMap);
		
		return new ListDataInfo<ResAccount>(list, totalCount);
	}
	
	public ListDataInfo<ResAccount> getAccountListByGroupId(int groupId) {
		System.out.println(groupId);
		int totalCount = accountRepository.selectAccountListByGroupIdCount(groupId);
		List<ResAccount> list = accountRepository.selectAccountListByGroupId(groupId);
		
		return new ListDataInfo<ResAccount>(list, totalCount);
	}
	
	public void addAccount(String userId, String userPassword, String userName, int groupId) {
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("userId", userId);
		paramMap.put("userPassword", userPassword);
		paramMap.put("userName", userName);
		paramMap.put("groupId", groupId);
		
		accountService.insertAccount(paramMap);
	}
	
	public ResAccount getSelectAccount(int accountIdx) {
		ResAccount resAccount = accountService.selectAccount(accountIdx);
		
		return resAccount;
	}
	
	@Transactional
	public void modifyAccounts(int groupId, int[] accountIdxs) {
		AuthAccount authAccount = SessionUtil.getSessionInfo();
		int registerId = authAccount.getAccountIdx();
		System.out.println(accountIdxs);
		accountRepository.updateAccounts(groupId, accountIdxs, registerId, Util.newDateString());
	}
	
	public void insertAccount(Map<String, Object> paramMap) {
		accountRepository.insertAccount(paramMap);
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
