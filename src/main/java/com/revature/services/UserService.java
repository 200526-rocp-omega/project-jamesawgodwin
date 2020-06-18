package com.revature.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;
import com.revature.templates.LoginTemplate;

// The service layer is a layer that is designed to enforce your "business logic"
// These are miscellaneous rules that define how your application will function
// 		Ex: May not withdraw money over the current balance (arbitrary rule only relevant to a bank, and not to Java)
// All interaction with the DAO will be through this service layer
// This design is simply furthering the same design structure that we have used up to now
// How you go about designing the details of this layer is up to you
// Due to the nature of the "business logic" being rather arbitrary
// This layer has the most creativity involved
// Most other layers are pretty boilerplate, where you pretty much copy/past most methods
public class UserService {
	
	
	private IUserDAO dao = new UserDAO();
	// A nice starting place I like to use is to create CRUD methods in the service layer
	// that will be used to interact with the DAO
	
	// The additionally, you can have extra methods to enforce whatever feature/rules that you want
	// Ex - we might also want a login/logout method
	
	//methods for C,R,U,D
	public int insert(User u) {
		return dao.insert(u);
	} //CREATE operation
	
	public List<User> findAll() {
		return dao.findAll();
	} // READ
	public User findById(int id) {
		return dao.findById(id);
	}
	public User findByUsername(String username) {
		return dao.findByUsername(username);
	}
	
	public int update(User u) {
		return dao.update(u);
	} //UPDATE
	
	public int delete (int id) {
		return dao.delete(id);
	} //DELETE

	public User login(LoginTemplate lt) {
		
		User userFromDB = findByUsername(lt.getUsername());
		
		// User name was incorrect
		if(userFromDB == null) {
			return null;
		}
		
		// Username was correct and so was password
		if(userFromDB.getPassword().equals(lt.getPassword())) {
			return userFromDB;
		}
		// Username was correct by password was not
		return null;
	} //Check if user is logged in
	public void logout(HttpSession session) {
		if(session == null || session.getAttribute("currentUser") == null) {
			throw new NotLoggedInException("User must be logged in, in order to logout");
			
		}
		
		session.invalidate();
	}
}
