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
		return userService.find(username);
	}
	
	@GetMapping("/@{username}/followers")
	public List<User> getFollowers(@PathVariable String username) {
		User user = userService.find(username);
		return user.getFollowing();
	}
	
	@PostMapping()
	public User postUsers(@RequestBody User user) {
		return userService.add(user);
	}

	@PostMapping("/@{username}/follow")
	public void followUser(@PathVariable String username, @RequestBody NewTweet credentials) {
		User followed = userService.checkPassword(credentials.getUsername(), credentials.getPassword());
		User follower = userService.find(username);
		List<User> following = new ArrayList<User>();
		if (follower.getFollowing() == null) {
			following = new ArrayList<User>();
		}
		else {
			following = follower.getFollowing();
		}
		boolean alreadyFollowing = following.contains(followed);
		if (followed != null && follower != null && !alreadyFollowing) {
			following.add(followed);
			follower.setFollowing(following);
			//Updates the user doesnt create new
			userService.add(follower);
		}
		else {
			// give error
		}
		
	}
	
	@PostMapping("/@{username}/unfollow")
	public void unfollowUser(@PathVariable String username, @RequestBody NewTweet credentials) {
		User followed = userService.checkPassword(credentials.getUsername(), credentials.getPassword());
		User follower = userService.find(username);
		List<User> following;
		if (follower.getFollowing() == null) {
			following = new ArrayList<User>();
		}
		else {
			following = follower.getFollowing();
		}
		if (following == null) following = new ArrayList<User>();
		boolean alreadyFollowing = following.contains(followed);
		if (followed != null && follower != null && alreadyFollowing) {
			following.remove(followed);
			follower.setFollowing(following);
			//Updates the user doesnt create new
			userService.add(follower);
		}
		else {
			// give error
		}
	}
	
}
