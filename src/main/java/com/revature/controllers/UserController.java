 package com.revature.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.authorization.AuthService;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserAccountService;
import com.revature.services.UserService;

public class UserController {

	private final UserService userService = new UserService();
	private final UserAccountService uas = new UserAccountService();
	private final AccountService as = new AccountService();
	
	public boolean logout(HttpSession session) {
		try {
			userService.logout(session);
		} catch(NotLoggedInException e) {
			return false;
		}
		return true;
	}
	
	public User findUserById(int id) {		
		return userService.findById(id);		
	}
	
	public List<User> findAllUsers() {		
		return userService.findAll();
	}

	public User create(User newUser) {
		// TODO Auto-generated method stub
		return null;
	}	

//	public User create(User u) {
//		return userService.insert(u);
//	}

//	public User update(int id, User u) {
//		return userService.update(id, u);
//	}
}
