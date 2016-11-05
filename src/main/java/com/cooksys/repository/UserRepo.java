package com.cooksys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.User;
import com.cooksys.projection.UserProjection;

public interface UserRepo extends JpaRepository<User, Long>{

	UserProjection findByUsername(String username);
	
	User findByUsernameAndPassword(String username, String password);
	
}
