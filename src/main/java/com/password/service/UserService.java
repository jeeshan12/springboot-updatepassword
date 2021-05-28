package com.password.service;

import org.springframework.stereotype.Service;

import com.password.model.User;

@Service
public interface UserService {

	public User findByIdAndOldPassword(Long id, String oldPassword);
	public User save(User user);
}
