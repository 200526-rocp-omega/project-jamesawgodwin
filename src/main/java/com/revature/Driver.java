package com.revature;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Role;
import com.revature.models.User;

public class Driver {

	public static void main(String[] args) {

		//ConnectionUtil.getConnection();
		/*
		 * Want layers
		 * layer/package for business logic
		 * layer/package for main
		 * package for db interactions
		 * package for drivers
		 * layers for all the things to logically separate aspects of our program -> different packages
		 */

		IUserDAO dao = new UserDAO();
		
		// for ID use 0 as ID when adding new db records
		User testUser = new User(0, "username", "password44", "first", "last", "email@email.com", new Role(1, "Standard"));
		System.out.println(dao.insert(testUser));
		
		for(User u : dao.findAll()) {
			System.out.println(u);
		}
			
			
			
			
			
	}

}
