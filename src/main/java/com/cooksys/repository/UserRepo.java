package com.cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

	User findByUsername(String username);
	
	User findByUsernameAndPassword(String username, String password);

	List<User> findByFollowing(User user);
	
}
