package com.aydinseven.hibernate;

import java.util.List;

import com.aydinseven.hibernate.model.User;

public class UserDAOImpl implements UserDAO {

	public Boolean saveUser(User user) {
		
		Boolean saveSuccessful = HibernateUtil.save(user);
		return saveSuccessful; 
	}

	public User updateUser(User u) {
		HibernateUtil.update(u);
		return u;
	}

	public void deleteUser(User u) {
		HibernateUtil.delete(u);
	}
	
	public User getUserByUsername(String username) {
		
		return (User) HibernateUtil.getUserByUsername(username);
	}

	public List<User> getUsers() {
		return (List<User>) HibernateUtil.getList(User.class);
	}
}