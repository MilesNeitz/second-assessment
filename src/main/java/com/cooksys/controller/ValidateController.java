package com.cooksys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.service.HashtagService;
import com.cooksys.service.TweetService;
import com.cooksys.service.UserService;

@RestController
@RequestMapping("validate")
public class ValidateController {
	
	TweetService tweetService;
	
	HashtagService hashtagService;
	
	UserService userService;
	
	public ValidateController(TweetService tweetService, UserService userService) {
		this.tweetService = tweetService;
		this.userService = userService;
	}
	
	@GetMapping("/tag/exists/{label}")
	public boolean checkTag(@PathVariable String label) {
		return hashtagService.findLabel(label.replace("{", "").replace("}", ""));
	}
	
	@GetMapping("/username/available/@{username}")
	public boolean checkAvaiable(@PathVariable String username) {
		return !userService.findUser(username.replace("{", "").replace("}", ""));
	}
	@GetMapping("/username/exists/@{username}")
	public boolean checkExists(@PathVariable String username) {
		return userService.findUser(username.replace("{", "").replace("}", ""));
	}
	
	
}
