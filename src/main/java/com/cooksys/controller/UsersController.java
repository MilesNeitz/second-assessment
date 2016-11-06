package com.cooksys.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.User;
import com.cooksys.service.TweetService;
import com.cooksys.service.UserService;

@RestController
@RequestMapping("users")
public class UsersController {
	
	TweetService tweetService;
	
	UserService userService;
	
	public UsersController(TweetService tweetService, UserService userService) {
		this.tweetService = tweetService;
		this.userService = userService;
	}
	
	@GetMapping()
	public List<User> getUsers() {
		return userService.findAll();
	}
	
	@GetMapping("/@{username}")
	public User getUser(@PathVariable String username) {
		return userService.find(username.replace("{", "").replace("}", ""));
	}
	
	
	@PostMapping()
	public User postUsers(@RequestBody User user) {
		return userService.add(user);
	}
	
}
