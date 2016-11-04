package com.cooksys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.entity.User;
import com.cooksys.repository.UserRepo;

@Service
public class UserService {

	private UserRepo userRepo;

	public UserService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	public User get(Long id) {
		return userRepo.getOne(id);
	}
	
	public List<User> findAll() {
		return userRepo.findAll();
	}
	
	public User add(User user) {
		return (userRepo.saveAndFlush(user));
	}
	
}
