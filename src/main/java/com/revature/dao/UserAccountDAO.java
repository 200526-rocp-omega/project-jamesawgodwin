package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountInfo;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.models.UserAccount;
import com.revature.models.UserInfo;
import com.revature.util.ConnectionUtil;

public class UserAccountDAO implements IUserAccountDAO{

	@Override
	public boolean createJoin(int user_id, int account_id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO USERS_ACCOUNTS VALUES (?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			stmt.setInt(2, account_id);

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch(SQLException e) {
			
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<UserAccount> findAll() {
		List<UserAccount> useraccounts = new ArrayList<>(); // ua too confusing

		try (Connection conn = ConnectionUtil.getConnection()) { // not confident in logic here --> investigate if not working
			String sql = "SELECT * FROM USERS_ACCOUNTS" 
					+ "INNER JOIN USERS ON user_id = USERS.id"
					+ "INNER JOIN ROLES ON USERS.role_id = ROLES.id"
					+ "INNER JOIN ACCOUNTS ON account_id = ACCOUNTS.id"
					+ "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_type.id"
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id";
					
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				
				int user_id = rs.getInt("user_id");
				int account_id = rs.getInt("account_id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String email = rs.getString("email");

				String role = rs.getString("role");
				int role_id = rs.getInt("role_id");
				
				double balance = rs.getDouble("balance");
				int status_id = rs.getInt("status_id");
				String status = rs.getString("status");
				int type_id = rs.getInt("type_id");
				String type = rs.getString("type");

				Role r = new Role(role_id, role);
				User u = new User(user_id, username, password, firstName, lastName, email, r);
				
				
				AccountType at = new AccountType(type_id, type);
				AccountStatus as = new AccountStatus(status_id, status);
				Account a = new Account(account_id, balance, as, at);
				
				UserAccount ua = new UserAccount(user_id, account_id, u, a);
				
				useraccounts.add(ua);
			}

		} catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return useraccounts;
	}

	@Override
	public List<AccountInfo> findAccountByUser(int user_id) {
		List<AccountInfo> accounts = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS"
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id"
					+ "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id"
					+ "INNER JOIN USERS ON user_id = USERS.id"
					+ "INNER JOIN USERS_ACCOUNTS ON ACCOUNTS.id = USERS_ACCOUNTS.account_id"	
					+ "INNER JOIN ROLES ON role_id = ROLES.id"
					+ "WHERE USERS.id = ? ORDER BY ACCOUNTS.id";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				
			
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				String role = rs.getString("role");
				
				double balance = rs.getDouble("balance");
				String status = rs.getString("status");
				String type = rs.getString("type");
				int account_id = rs.getInt("account_id");

				UserInfo u = new UserInfo(user_id, username, password, firstName, lastName, email, role);
				AccountInfo a = new AccountInfo(account_id, balance, status, type, u);
				accounts.add(a);
			}
		} catch(SQLException e) {
			
			e.printStackTrace();
			//return empty list if error
			return new ArrayList<>();
		}
		return accounts;
	}

	@Override
	public List<User> findUserByAccount(int account_id) {
		List<User> users = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS_ACCOUNTS"
					+ "INNER JOIN ROLES ON USERS.role_id = ROLES.id" 
					+ "INNER JOIN USERS ON user_id = USERS.id"
					+ "WHERE account_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, account_id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				
			
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int role_id = rs.getInt("role_id");
				
				String role = rs.getString("role");
				int user_id = rs.getInt("user_id");

				Role r = new Role(role_id, role);
				User u = new User(user_id, username, password, firstName, lastName, email, r);
				
				users.add(u);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			// return empty list if error
			return new ArrayList<>();
		}
		return users;
	}

	@Override
	public UserAccount findAccountByPK(int user_id, int account_id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS_ACCOUNTS" 
					+ "INNER JOIN USERS ON user_id = USERS.id"
					+ "INNER JOIN ROLES ON USERS.role_id = ROLES.id"
					+ "INNER JOIN ACCOUNTS ON account_id = ACCOUNTS.id"
					+ "INNER JOIN ACCOUNT_STATUS ON status_id = ACCOUNT_STATUS.id"
					+ "INNER JOIN ACCOUNT_TYPE ON type_id = ACCOUNT_TYPE.id"
					+ "WHERE user_id = ? AND account_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, user_id);
			stmt.setInt(2, account_id);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int role_id = rs.getInt("role_id");
				String role = rs.getString("role");
				
				int status_id = rs.getInt("status_id");
				String status = rs.getString("status");
				int type_id = rs.getInt("type_id");
				String type = rs.getString("type");
				double balance = rs.getDouble("balance");

				Role r = new Role(role_id, role);
				User u = new User(user_id, username, password, firstName, lastName, email, r);
				
				AccountStatus as = new AccountStatus(status_id, status);
				AccountType at = new AccountType(type_id, type);
				Account a = new Account(account_id, balance, as, at);
				
				UserAccount ua = new UserAccount(user_id, account_id, u, a);
				
				return ua;
				
			} else {
				
				return new UserAccount();
			}
		} catch(SQLException e) {
			
			e.printStackTrace();
			return new UserAccount();
		 }
	}

	@Override
	public List<AccountInfo> findByAccount(int account_id) {
		List<AccountInfo> accounts = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS"
					+ "INNER JOIN USERS_ACCOUNTS ON ACCOUNTS.id = USERS_ACCOUNTS.account_id"
					+ "INNER JOIN USERS ON user_id = USERS.id" 
					+ "INNER JOIN ROLES ON role_id = ROLES.id"
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id"
					+ "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id"
					+ "WHERE ACCOUNTS.id = ? "
					+ "ORDER BY ACCOUNTS.id";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, account_id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				
		
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				String role = rs.getString("role");
				
				int user_id = rs.getInt("user_id");
				double balance = rs.getDouble("balance");
				String status = rs.getString("status");
				String type = rs.getString("type");

				UserInfo u = new UserInfo(user_id, username, password, firstName, lastName, email, role);
				AccountInfo a = new AccountInfo(account_id, balance, status, type, u);
				
				accounts.add(a);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			// if error then return empty list
			return new ArrayList<>();
		}
		return accounts;
	}

	@Override
	public boolean deleteJoin(int user_id, int account_id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM USERS_ACCOUNTS WHERE user_id = ? AND account_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			stmt.setInt(2, account_id);

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch(SQLException e) {
			
			e.printStackTrace();
			return false;
			
		}
	}

}
