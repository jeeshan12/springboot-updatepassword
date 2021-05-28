package com.password.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.password.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long>{
	
	public User findByIdAndOldPassword(Long id, String oldPassword);
	
}
