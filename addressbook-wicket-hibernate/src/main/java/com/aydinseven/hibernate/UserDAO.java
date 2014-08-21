package com.aydinseven.hibernate;

import java.util.List;

import com.aydinseven.hibernate.model.User;

public interface UserDAO {
	
	Boolean saveUser(User user);
	
	User getUserByUsername (String username);
	
	List<User> getUsers();
	
	User updateUser(User u);
	
	void deleteUser(User u);
}