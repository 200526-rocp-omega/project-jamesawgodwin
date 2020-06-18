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
import com.revature.models.UserAccount;
import com.revature.templates.AccountTemplate;
import com.revature.templates.AccountTransferTemplate;
import com.revature.templates.MessageTemplate;
import com.revature.templates.TransactionsTemplate;

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
						
						AuthService.guard(session, "Employee", "Admin");
						List<AccountInfo> accounts = accountController.findAccountsByStatus(id);
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(accounts));
					} else if (portions[1].equals("owner")) {
						AuthService.guard(session, "Employee", "Admin");
						List<AccountInfo> accounts = accountController.findAccountsByUser(session, id);
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(accounts));
					} else if (portions[1].equals("type")) {
						AuthService.guard(session, "Employee", "Admin");
						List<AccountInfo> accounts = accountController.findAccountsByType(id);
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(accounts));		
					} else {
						res.setStatus(400);
						MessageTemplate message = new MessageTemplate("Invalid URL");
						res.getWriter().println(om.writeValueAsString(message));
					}
				
				}
				break;
				
			
			default:
					res.setStatus(404);
					MessageTemplate message = new MessageTemplate("Invalid URL");
					res.getWriter().println(om.writeValueAsString(message));
			
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
				
			case "users":
				User newUser = om.readValue(req.getReader(), User.class);
				User createdUser = userController.create(newUser);
				if (createdUser == null) {
					res.setStatus(400);
					MessageTemplate message = new MessageTemplate("Error creating user");
					res.getWriter().println(om.writeValueAsString(message));
				}
				res.setStatus(201);
				MessageTemplate message = new MessageTemplate("New user created");
				res.getWriter().println(om.writeValueAsString(message));
				break;
				
			case "accounts":
				if (portions.length == 1) {
					AccountTemplate newacc = om.readValue(req.getReader(), AccountTemplate.class);
					AuthService.guard(req.getSession(false), newacc.getUser_id(), "Admin");
					UserAccount createdAcct = accountController.create(newacc);
					if (createdAcct == null) {
						res.setStatus(400);
						message = new MessageTemplate("Error creating account");
						res.getWriter().println(om.writeValueAsString(message));
					}
					res.setStatus(201);
					res.getWriter().println(om.writeValueAsString(createdAcct));
				} else {
					switch (portions[1]) {
					case "withdraw":
						TransactionsTemplate withdraw = om.readValue(req.getReader(), TransactionsTemplate.class);
						AuthService.guard(accountController.findById(withdraw.getAccountId()));
						if (accountController.withdraw(req.getSession(false), withdraw)) {
							res.setStatus(200);
							message = new MessageTemplate(
									"$" + withdraw.getTransactionalAmount() 
									+ " has been withdrawn from Account #" 
									+ withdraw.getAccountId());
							res.getWriter().println(om.writeValueAsString(message));
						} else {
							res.setStatus(400);
							message = new MessageTemplate("Insufficient Funds");
							res.getWriter().println(om.writeValueAsString(message));
						}
						break;
					case "deposit":
						TransactionsTemplate dep = om.readValue(req.getReader(), TransactionsTemplate.class);
						AuthService.guard(accountController.findById(dep.getAccountId()));
						if (accountController.deposit(req.getSession(false), dep)) {
							res.setStatus(200);
							message = new MessageTemplate(
									"$" + dep.getTransactionalAmount()
									+ " has been deposited to Account #" 
									+ dep.getAccountId());
							res.getWriter().println(om.writeValueAsString(message));
						} else {
							res.setStatus(400);
							message = new MessageTemplate("Insufficient Funds");
							res.getWriter().println(om.writeValueAsString(message));
						}
						break;
						
					case "transfer":
						AccountTransferTemplate tran = om.readValue(req.getReader(), AccountTransferTemplate.class);
						AuthService.guard(accountController.findById(tran.getFromAccountId()));
						AuthService.guard(accountController.findById(tran.getToAccountId()));
						if (accountController.transfer(req.getSession(false), tran)) {
							res.setStatus(202);
							message = new MessageTemplate("$" + tran.getTransactionAmount() + " has been transferred from Account #"
									+ tran.getFromAccountId() + " to Account #" + tran.getToAccountId());
							res.getWriter().println(om.writeValueAsString(message));
						} else {
							res.setStatus(400);
							message = new MessageTemplate("Insufficient Funds");
							res.getWriter().println(om.writeValueAsString(message));
						}
						break;
			}
				}
			}
		 } catch(NotLoggedInException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message)); // serializing message template
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		String[] portions = URI.split("/");
		HttpSession session = req.getSession();
		res.setContentType("application/json");
		
		try {
			
			switch (portions[0]) {
			case "users" :
			}
		} catch(AuthorizationException e) {
			{
				res.setStatus(401);
				MessageTemplate message = new MessageTemplate("The incoming token has expired");
				res.getWriter().println(om.writeValueAsString(message));
			}
		}
		
	}
	
}
