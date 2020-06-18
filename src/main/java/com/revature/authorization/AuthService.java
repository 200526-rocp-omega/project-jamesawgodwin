package com.revature.authorization;

import java.util.Set;

import javax.servlet.http.HttpSession;

import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.RoleNotAllowedException;
import com.revature.models.Account;
import com.revature.models.User;

public class AuthService {
	
	public static void guard(HttpSession session, String...roles) {
		
		User currentUser = session == null ? null : (User) session.getAttribute("currentUser");
		// if no session      if session but not logged in (edge case)
		if(session == null || currentUser == null) {
			throw new NotLoggedInException();
			
		} 
		boolean found = false;
		String role = currentUser.getRole().getRole(); // get role in User object then get role in Role object
		
		for(String allowedRole : roles) {
			if(allowedRole.equals(role)) {
				found = true;
			}
		}
		
		if(!found) {
			throw new RoleNotAllowedException();
		}
	}
	// I am overloading this method to also include an id parameter
	public static void guard(HttpSession session, int id, String...roles) {
		//leverage the other guard method
		
		try {
			guard(session, roles);
		} catch(RoleNotAllowedException e) {
			User current = (User) session.getAttribute("currentUser");
			if(id != current.getUserId()) {
				throw e;
				
			}
		}
	}
	
//	
	
	public static void guard(HttpSession session, Set<Integer> ids, String... roles) {
		try {
			guard(session, roles);
		} catch (RoleNotAllowedException e) {
			User u = (User) session.getAttribute("currentUser");
			for (int id : ids) {
				if (u.getUserId() == id) {
					return;
				}
			}
			throw e;
		}
	}
	
	public static void guard(Account a) {
		if (a.getStatus().getStatusId() != 2) {
			throw new RoleNotAllowedException();
		}
	}
	
	

}
