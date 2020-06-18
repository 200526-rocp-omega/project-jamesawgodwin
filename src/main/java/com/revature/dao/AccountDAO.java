package com.revature.dao;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountInfo;

public class AccountDAO implements IAccountDAO {

	@Override
	public List<AccountInfo> findAll() {
		return null;
	}

	@Override
	public Account insert(Account a) {
		return null;
	}

	@Override
	public List<AccountInfo> findByAccountStatus(int status_id) {
		return null;
	}

	@Override
	public List<AccountInfo> findByAccountTypeInfo(int type_id) {
		return null;
	}

	@Override
	public Account update(int id, Account a) {
		return null;
	}

	@Override
	public List<Account> findByAccountType(int type_id) {
		return null;
	}

	@Override
	public Account findByAccountId(int id) {
		return null;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

}
