package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDAO implements IUserDAO {

	@Override
	public int insert(User u) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			// The ? marks are placeholders for input values
			// They work for prepared statements and are designed to protect from SQL injection
			String columns = "username, password, first_name, last_name, email, role_id";
			String sql = "INSERT INTO USERS (" + columns + ") VALUES (?, ?, ?, ?, ?, ?)";
			
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			// We insert values into each of the ? above
		
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getFirstName());
			stmt.setString(4, u.getLastName());
			stmt.setString(5, u.getEmail());
			stmt.setInt(6, u.getRole().getRoleId());
			
			return stmt.executeUpdate();		
	
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}		
		
	}

	@Override
	public List<User> findAll() {

		//connection -> statement -> query, iterate over query
		
		List<User> allUsers = new ArrayList<>();
		
		// need to check for SQL exceptions
		// Below is a try-with-resources block, works with things that implement AutoClosable interface
		// It allows us to instantiate some variable for use only inside the try block
		// And then at the end, it will automatically invoke the close() method on the resource
		// The close() method prevents memory leaks -> implements AutoClosable interface
		try (Connection conn = ConnectionUtil.getConnection()) {
			//execute a SQL statement on our db
			String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id"; 
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql); //allows us to execute SQL operations on our db from java application
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String email = rs.getString("email");
				int role_id = rs.getInt("role_id");
				String role_name = rs.getString("role");
				
				Role role = new Role(role_id, role_name);
				User u = new User(id, username, password, first_name, last_name, email, role);
				
				allUsers.add(u);
				
				
			}
			
		} catch(SQLException e) {
			
			//if something goes wrong, return an empty list
			e.printStackTrace();
			return new ArrayList<>();
		}
		
		return allUsers;
	}


    @Override
    public User findById(int id) {
        try (Connection conn = ConnectionUtil.getConnection()) {
        	
            String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id WHERE USERS.id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role");
                
                // And use that data to create a User object accordingly
                Role role = new Role(roleId, roleName);
                return new User(id, username, password, firstName, lastName, email, role);
            }
            
        } catch(SQLException e) {
        	
            e.printStackTrace();
        }
        
        return null;
        
    }


	@Override
	public int update(User u) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "UPDATE USERS SET username = ?, password = ?, first_name = ?, last_name = ?, email = ?, role_id = ? WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getFirstName());
			stmt.setString(4, u.getLastName());
			stmt.setString(5, u.getEmail());
			stmt.setInt(6, u.getRole().getRoleId());
			stmt.setInt(7, u.getUserId());


			if (stmt.executeUpdate() > 0) {
				
				return stmt.executeUpdate();
				
			} else {
				
				//System.out.println("User not updated");
				return -1; // is needed?
			}


		} catch(SQLException e) {
			
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int delete(int id) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM USERS where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,id);

			if (stmt.executeUpdate() > 0) {
				return stmt.executeUpdate();
			} return 0;


		} catch(SQLException e) {
			e.printStackTrace();
			return -1; // is right?
		}
	}
	

	@Override
    public User findByUsername(String username) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
            	
                int id = rs.getInt("id");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role");
                
                // And use that data to create a User object accordingly
                Role role = new Role(roleId, roleName);
                return new User(id, username, password, firstName, lastName, email, role);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

	@Override
	public User update(int id, User u) {
		// TODO Auto-generated method stub
		return null;
	}

}
