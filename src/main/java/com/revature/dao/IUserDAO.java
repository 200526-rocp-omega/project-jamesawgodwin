package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public interface IUserDAO {
	
	//methods for C,R,U,D
	public int insert(User u); //CREATE operation
	
	public List<User> findAll(); // READ
	
	public User findById(int id);
	
	public User findByUsername(String username);
	
	public int update(User u); //UPDATE
	
	public int delete (int id); //DELETE

	public User update(int id, User u);


}
