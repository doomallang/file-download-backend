package com.innotium.npouch.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.innotium.npouch.dto.ReqAccount;
import com.innotium.npouch.dto.ResAccount;
import com.innotium.npouch.util.Util;

@Mapper
public interface AccountRepository {
	public int isAccountExist(@Param("accountId") String accountId);
	
	public int selectAccountListCount(Map<String, Object> paramMap);
	
	public List<ResAccount> selectAccountList(Map<String, Object> paramMap);
	
	public int selectAccountListByGroupIdCount(@Param("groupId") int groupId);
	
	public List<ResAccount> selectAccountListByGroupId(@Param("groupId") int groupId);
	
	public void insertAccount(Map<String, Object> paramMap);
	
	public void updateAccount(Map<String, Object> paramMap);
	
	public void deleteAccount(@Param("accountIdxs") int[] accountIdxs);
	
	public ResAccount selectAccount(@Param("accountIdx") int accountIdx);
	
	public ResAccount selectAccountByAccountId(@Param("accountId") String accountId);
	
	public int selectAccountIdExists(@Param("accountId") String accountId);
	
	public void updateAccounts(@Param("groupId") int groupId, @Param("accountIdxs") int[] accountIdxs, @Param("updateId") int updateId, @Param("updateDatetime") String updateDatetime);

}
