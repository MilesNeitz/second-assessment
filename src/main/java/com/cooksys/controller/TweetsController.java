package com.cooksys.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.classes.NewTweet;
import com.cooksys.entity.Tweet;
import com.cooksys.entity.User;
import com.cooksys.service.TweetService;
import com.cooksys.service.UserService;

@RestController
@RequestMapping("tweets")
public class TweetsController {
	
	TweetService tweetService;
	
	UserService userService;
	
	public TweetsController(TweetService tweetService, UserService userService) {
		this.tweetService = tweetService;
		this.userService = userService;
	}
	
	@GetMapping()
	public List<Tweet> getTweets() {
		return tweetService.findAllTweets();
	}
	
	@PostMapping()
	public Tweet postTweet(@RequestBody NewTweet newTweet) {
		Tweet tweet = new Tweet();
		// case sensitive 
		
		User author = userService.checkPassword(newTweet.getUsername(), newTweet.getPassword());
		if (author != null) {
			tweet.setContent(newTweet.getContent());
			tweet.setAuthor(author);
		}
		
		return tweetService.addTweet(tweet);
	}
}
