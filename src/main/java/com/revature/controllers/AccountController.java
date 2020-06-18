package com.revature.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.revature.authorization.AuthService;
import com.revature.models.Account;
import com.revature.models.AccountInfo;
import com.revature.models.User;
import com.revature.models.UserAccount;
import com.revature.services.AccountService;
import com.revature.services.UserAccountService;
import com.revature.templates.AccountTemplate;
import com.revature.templates.AccountTransferTemplate;
import com.revature.templates.InterestTemplate;
import com.revature.templates.JointAccountTemplate;
import com.revature.templates.TransactionsTemplate;


/*
 * All the fun logic for joining accounts and users
 * 
 * IMPORT ALL THE THINGS
 */
public class AccountController {
	
	double interest_rate = .157; // It is the 1970s again -> inflation is even higher tho
	private final UserAccountService uas = new UserAccountService();
	private final AccountService as = new AccountService();
	
	public Account findById(int id) {
		return as.findById(id);
	}
	
	public List<AccountInfo> findAllAccounts() {
		return as.findAll();
	}
	
	public List<AccountInfo> findAccountByID(HttpSession session, int id) {
		List<User> au = uas.findUserByAccount(id);
		Set<Integer> userIds = new HashSet<>();
		
		for (User u : au) {
			userIds.add(u.getUserId());
		}
		// Special admin privileges
		AuthService.guard(session, userIds, "Admin");
		return uas.findByAccount(id);
	}
	
	public List<AccountInfo> findAccountsByStatus(int status_id) {
		return as.findByStatus(status_id);
	}
	
	public List<AccountInfo> findAccountsByUser(HttpSession session, int id) {
		return uas.findAccountByUser(id);
	}

	public List<AccountInfo> findAccountsByType(int type_id) {
		return as.findByTypeDisplay(type_id);
	}

	public UserAccount create(AccountTemplate at) {
		Account na = as.insert(at.getAccount());
		uas.createJoin(at.getUser_id(), na.getAccountId());
		
		// Does primary key work?
		return uas.findByPK(at.getUser_id(), na.getAccountId());
	}

	public Account update(Account a, int id) {
		return as.update(id, a);
	}

	public List<AccountInfo> addJoint(HttpSession session, JointAccountTemplate jat) {
		User u = (User) session.getAttribute("currentUser");
		if (uas.findUserByAccount(jat.getAccount_id()).get(0).getUserId() != u.getUserId()) {
			return null;
		}
		if (uas.createJoin(jat.getUser_id(), jat.getAccount_id())) {
			return uas.findByAccount(jat.getAccount_id());
		}
		return null;
	}

	public boolean withdraw(HttpSession session, TransactionsTemplate tt) {
		List<User> acc_use = uas.findUserByAccount(tt.getAccountId());
		Set<Integer> userIds = new HashSet<>();
		
		for (User u : acc_use) {
			userIds.add(u.getUserId());
		}
		
		// Admin privilege to withdraw money
		AuthService.guard(session, userIds, "Admin");
		Account acc = as.findById(tt.getAccountId());
		
		if (acc.getBalance() >= tt.getTransactionalAmount()) {
			// Update account if permissions valid and enough money is in account to cover tx
			Account update_acc = new Account(acc.getAccountId(), acc.getBalance() - tt.getTransactionalAmount(),
					acc.getStatus(), acc.getType());
			as.update(tt.getAccountId(), update_acc);
			return true;
		} // no negative monies!
		return false;
	}

	public boolean deposit(HttpSession session, TransactionsTemplate tt) {
		List<User> acc_use = uas.findUserByAccount(tt.getAccountId());
		Set<Integer> userIds = new HashSet<>();
		
		for (User u : acc_use) {
			userIds.add(u.getUserId());
		}
		
		AuthService.guard(session, userIds, "Admin");
		Account acc = as.findById(tt.getAccountId());
		// update account with deposit tx
		Account update_acc = new Account(acc.getAccountId(), acc.getBalance() + tt.getTransactionalAmount(), 
				acc.getStatus(), acc.getType());
		
		if (tt.getTransactionalAmount() < 0) {
		    throw new IllegalArgumentException();
		}

		as.update(tt.getAccountId(), update_acc);
		// consider throwing exception for negative number!
		return true;
	}

	public boolean transfer(HttpSession session, AccountTransferTemplate att) {
		List<User> acc_use = uas.findUserByAccount(att.getFromAccountId());
		Set<Integer> userIds = new HashSet<>();
		for (User u : acc_use) {
			
			userIds.add(u.getUserId());
		}
		
		AuthService.guard(session, userIds, "Admin");
		Account source = as.findById(att.getFromAccountId());
		Account target = as.findById(att.getToAccountId());
		if (att.getTransactionAmount() < 0) {
		    throw new IllegalArgumentException();
		}
		if (source.getBalance() >= att.getTransactionAmount()) {
			
			Account withdraw_acc = new Account(source.getAccountId(), source.getBalance() - att.getTransactionAmount(), 
					source.getStatus(),	source.getType());
			
			Account deposit_acc = new Account(target.getAccountId(), target.getBalance() + att.getTransactionAmount(), 
					target.getStatus(), target.getType());
			
			as.update(att.getFromAccountId(), withdraw_acc);
			as.update(att.getToAccountId(), deposit_acc);
			return true;
		} 
		
		return false;
	}

	public void accrueInterest(InterestTemplate it) {
		List<Account> savings = as.findByType(2);
		for (Account a : savings) {
			double interest = it.getNumOfMonths() * (a.getBalance() * interest_rate);
			Account getInterest = new Account(a.getAccountId(), a.getBalance() + interest, a.getStatus(), a.getType());
			as.update(a.getAccountId(), getInterest);
		}
	}

	public boolean delete(HttpSession session, int id) {
		List<User> acctUsers = uas.findUserByAccount(id);
		Set<Integer> userIds = new HashSet<>();
		for (User u : acctUsers) {
			userIds.add(u.getUserId());
		}
		// allow Employee and Admin access
		AuthService.guard(session, userIds, "Employee", "Admin");
		return as.delete(id);
	}

}