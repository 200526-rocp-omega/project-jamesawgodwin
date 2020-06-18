package com.revature.services;

import java.util.List;

import com.revature.dao.AccountDAO;
import com.revature.dao.IAccountDAO;
import com.revature.models.Account;
import com.revature.models.AccountInfo;

public class AccountService {
	private IAccountDAO dao = new AccountDAO();

	public Account insert(Account a) {
		return dao.insert(a);
	}

	public List<AccountInfo> findAll() {
		return dao.findAll();
	}

	public List<AccountInfo> findByStatus(int status_id) {
		return dao.findByAccountStatus(status_id);
	}

	public List<AccountInfo> findByTypeDisplay(int type_id) {
		return dao.findByAccountTypeInfo(type_id);
	}

	public List<Account> findByType(int type_id) {
		return dao.findByAccountType(type_id);
	}

	public Account findById(int id) {
		return dao.findByAccountId(id);
	}

	public Account update(int id, Account a) {
		return dao.update(id, a);
	}

	public boolean delete(int id) {
		return dao.delete(id);
	}
}
