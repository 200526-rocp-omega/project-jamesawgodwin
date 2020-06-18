package com.revature.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.authorization.AuthService;
import com.revature.controllers.AccountController;
import com.revature.controllers.UserController;
import com.revature.exceptions.AuthorizationException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.AccountInfo;
import com.revature.models.User;
import com.revature.templates.MessageTemplate;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = -4854248294011883310L;
	private static final UserController userController = new UserController();
	private static final ObjectMapper om = new ObjectMapper();
	private static final AccountController accountController = new AccountController();


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);
		res.setContentType("application/json");
		res.setStatus(404);
		
		// Prevents non-desired endpoints from being successful
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		
		String[] portions = URI.split("/");
		try { 
			switch(portions[0]) {
			case "users":
				
				if(portions.length == 2) {
					
					int id = Integer.parseInt(portions[1]); // NEED UTILITY METHOD TO HANDLE .parseInt exception
					AuthService.guard(req.getSession(false), id, "Employee", "Admin");
					User u = userController.findUserById(id);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(u));
					
				} else {
					AuthService.guard(req.getSession(false), "Employee", "Admin");
					List<User> all = userController.findAllUsers();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(all));
					
				}
				
				break;
				
			case "accounts":
				
				if(portions.length == 3) {
					int id = Integer.parseInt(portions[2]);
					if (portions[1].equals("status")) {
						
					}
						AuthService.guard(session, "Employee", "Admin");
						List<AccountInfo> accounts = accountController.findAccountsByStatus(id);
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(accts));
				}
				break;
			}
		} catch(AuthorizationException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message)); // serializing message template
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		res.setContentType("application/json");
		res.setStatus(404);

		// Prevents non-desired endpoints from being successful
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");

		String[] portions = URI.split("/");
		
		try {
			switch(portions[0]) {
			case "logout":
				if(userController.logout(req.getSession(false))) {
					res.setStatus(200);
					res.getWriter().println("You have been successfully logged out");
				} else {
					res.setStatus(400);
					res.getWriter().println("You were not logged in to begin with");
				}
				break;
			}
		 } catch(NotLoggedInException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message)); // serializing message template
		}
	}
	
}
