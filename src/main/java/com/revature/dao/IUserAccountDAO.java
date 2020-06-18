package com.revature.dao;

import com.revature.models.UserAccount;
import com.revature.models.User;

import java.util.List;

import com.revature.models.AccountInfo;

public interface IUserAccountDAO {
		
		public boolean createJoin(int user_id, int account_id);

		public List<UserAccount> findAll();

		public List<AccountInfo> findAccountByUser(int user_id);

		public List<User> findUserByAccount(int account_id);

		public UserAccount findAccountByPK(int user_id, int account_id);

		public List<AccountInfo> findByAccount(int account_id);

		public boolean deleteJoin(int user_id, int account_id);
	

}
