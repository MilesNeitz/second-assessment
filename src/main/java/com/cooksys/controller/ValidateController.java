package com.cooksys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.Hashtag;
import com.cooksys.entity.User;
import com.cooksys.service.TweetService;
import com.cooksys.service.UserService;

@RestController
@RequestMapping("validate")
public class ValidateController {
	
	TweetService tweetService;
	
	UserService userService;
	
	public ValidateController(TweetService tweetService, UserService userService) {
		this.tweetService = tweetService;
		this.userService = userService;
	}
	
	@GetMapping("/tag/exists/{label}")
	public boolean checkTag(@PathVariable Hashtag hashtag) {
		return (hashtag != null);
	}
	
	@GetMapping("/username/exists/@{user}")
	public String checkExists(@RequestBody String user) {
		return (user);
	}
	@GetMapping("/username/available/@{username}")
	public boolean checkAvaiable(@RequestBody User user) {
		return (user == null);
	}
	
	
}
