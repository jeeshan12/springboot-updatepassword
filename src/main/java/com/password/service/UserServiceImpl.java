package com.password.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.password.dao.UserDao;
import com.password.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public User findByIdAndOldPassword(Long id, String oldPassword) {
		return userDao.findByIdAndOldPassword(id, oldPassword);
	}
	

	@Override
	public User save(User user) {
		return userDao.save(user);
	}

}
