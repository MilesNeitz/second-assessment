package com.cooksys.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.classes.NewTweet;
import com.cooksys.entity.Hashtag;
import com.cooksys.entity.Tweet;
import com.cooksys.entity.User;
import com.cooksys.service.HashtagService;
import com.cooksys.service.TweetService;
import com.cooksys.service.UserService;

@RestController
@RequestMapping("tweets")
public class TweetsController {
	
	TweetService tweetService;
	
	HashtagService hashtagService;
	
	UserService userService;
	
	public TweetsController(TweetService tweetService, UserService userService, HashtagService hashtagService) {
		this.tweetService = tweetService;
		this.userService = userService;
		this.hashtagService = hashtagService;
	}
	
	@GetMapping()
	public List<Tweet> getTweets() {
		return tweetService.findAllTweets();
	}
	
	@GetMapping("/{id}")
	public Tweet getTweet(@PathVariable Long id) {
		return tweetService.getTweet((long) 1);
	}
	
	@PostMapping("/{id}/like")
	public void postLike(@PathVariable Long id, @RequestBody NewTweet newTweet) {
		
		Tweet tweet = tweetService.getTweet(id);
		User user = userService.checkPassword(newTweet.getUsername(), newTweet.getPassword());
		if (!tweet.getLikes().contains(user)) {
			if (tweet.getLikes() == null) {
				List<User> users = new ArrayList<User>();
				users.add(user);
				tweet.setLike(users);
				tweetService.addTweet(tweet);
			}
			else {
				tweet.addLike(user);
				tweetService.addTweet(tweet);
			}
		}
		else {
			//give error
		}
	}
	
	// case sensitive 
	@PostMapping()
	public Tweet postTweet(@RequestBody NewTweet newTweet) {
		Tweet tweet = new Tweet();
		
		User author = userService.checkPassword(newTweet.getUsername(), newTweet.getPassword());
		if (author != null) {
			tweet.setContent(newTweet.getContent());
			tweet.setAuthor(author);
			String[] contents = newTweet.getContent().split("\\s+");
			for (String content : contents) {
	            if (content.substring(0, 1).equals("@")) {
	            		
	            }
	            if (content.substring(0, 1).equals("#")) {
	            	Hashtag hashtag = hashtagService.checkHashtag(content);
	            	
	            	if (hashtag != null) {
            			hashtag = hashtagService.checkHashtag(content);
	            		tweet.addTaged(hashtag);	            	
	            	}
	            	else {
	            		hashtag = new Hashtag();
	            		hashtag.setName(content);
	            		hashtagService.addHashtag(hashtag);
	            		List<Hashtag> hashtags = new ArrayList<Hashtag>();
	            		hashtags.add(hashtag);
	            		tweet.setTaged(hashtags);
	            	}
	            }
	        }
		}
		tweet = tweetService.addTweet(tweet);
		return tweet;
	}
}
