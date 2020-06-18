package com.revature.dao;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountInfo;

public interface IAccountDAO {

	List<AccountInfo> findAll();

	Account insert(Account a);

	List<AccountInfo> findByAccountStatus(int status_id);

	List<AccountInfo> findByAccountTypeInfo(int type_id);

	Account update(int id, Account a);

	List<Account> findByAccountType(int type_id);

	Account findByAccountId(int id);

	boolean delete(int id);

}
