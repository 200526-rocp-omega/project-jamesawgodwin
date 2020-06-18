package com.revature.services;

import java.util.List;

import com.revature.dao.IUserAccountDAO;
import com.revature.dao.UserAccountDAO;
import com.revature.models.AccountInfo;
import com.revature.models.User;
import com.revature.models.UserAccount;

public class UserAccountService {
	
	private IUserAccountDAO uad = new UserAccountDAO();
	
	public boolean createJoin(int user_id, int account_id) {
		return uad.createJoin(user_id, account_id);
	}
	
	public boolean deleteJoin(int user_id, int account_id) {
		return uad.deleteJoin(user_id, account_id);
	}

	public List<UserAccount> findAll(){
		return uad.findAll();
	}

	public UserAccount findByPK(int user_id, int account_id) {
		return uad.findAccountByPK(user_id, account_id);
	}
	
	public List<AccountInfo> findByAccount(int account_id){
		return uad.findByAccount(account_id);
	}
	
	public List<AccountInfo> findAccountByUser(int user_id){
		return uad.findAccountByUser(user_id);
	}

	public List<User> findUserByAccount(int account_id){
		return uad.findUserByAccount(account_id);
	}
}
