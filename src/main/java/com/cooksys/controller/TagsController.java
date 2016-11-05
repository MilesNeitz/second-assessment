package com.cooksys.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.Hashtag;
import com.cooksys.service.TweetService;
import com.cooksys.service.UserService;

@RestController
@RequestMapping("tags")
public class TagsController {
	
	TweetService tweetService;
	
	UserService userService;
	
	public TagsController(TweetService tweetService, UserService userService) {
		this.tweetService = tweetService;
		this.userService = userService;
	}
	
	@GetMapping()
	public List<Hashtag> getHashtags() {
		return tweetService.findAllHashtags();
	}	
	
	@GetMapping("/@{label}")
	public Hashtag getHashtag(@PathVariable String label) {
		return tweetService.findHashtag(label.replace("{", "").replace("}", ""));
	}
}
