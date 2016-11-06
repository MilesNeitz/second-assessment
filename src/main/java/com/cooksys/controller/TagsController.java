package com.cooksys.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.Hashtag;
import com.cooksys.service.HashtagService;
import com.cooksys.service.TweetService;
import com.cooksys.service.UserService;

@RestController
@RequestMapping("tags")
public class TagsController {
	
	TweetService tweetService;
	
	HashtagService hashtagService;
	
	UserService userService;
	
	public TagsController(TweetService tweetService, UserService userService, HashtagService hashtagService) {
		this.tweetService = tweetService;
		this.userService = userService;
		this.hashtagService = hashtagService;
	}
	
	@GetMapping()
	public List<Hashtag> getHashtags() {
		return hashtagService.findAll();
	}	
	
	@GetMapping("/@{label}")
	public Hashtag getHashtag(@PathVariable String label) {
		return hashtagService.checkHashtag(label.replace("{", "").replace("}", ""));
	}
}
