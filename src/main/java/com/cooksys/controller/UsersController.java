package com.cooksys.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.classes.NewTweet;
import com.cooksys.classes.PatchUser;
import com.cooksys.entity.Tweet;
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
		List<User> users = userService.findAll();
		users.removeIf(user -> user.getDeleted());
		return users;
	}
	
	@GetMapping("/@{username}")
	public User getUser(@PathVariable String username) {
		return userService.find(username);
	}
	
	@GetMapping("/@{username}/followers")
	public List<User> getFollowers(@PathVariable String username) {
		User followed = userService.find(username);
		List<User> users = userService.findByFollowing(followed);
		users.removeIf(user -> user.getDeleted());
		return users;
		
	}
	
	@GetMapping("/@{username}/following")
	public List<User> getFollowing(@PathVariable String username) {
		User follower = userService.find(username);
		List<User> users = follower.getFollowing();
		users.removeIf(user -> user.getDeleted());
		return users;
	}
	
	@GetMapping("@{username}/feed")
	public List<Tweet> getFeed(@PathVariable String username) {
		User user = userService.find(username);
		List<User> followers = user.getFollowing();
		List<Tweet> tweets = tweetService.findByAuthor(user);
		for (User follower : followers) {
			if (!follower.getDeleted()) tweets.addAll(tweetService.findByAuthor(follower));
			tweets.removeIf(tweet -> tweet.getDeleted());
		}
		Collections.sort(tweets);
		return tweets;
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
	
	@PatchMapping("/@{username}")
	public User patchUser(@RequestBody PatchUser patchUser) {
		User user = userService.checkPassword(patchUser.getUsername(), patchUser.getPassword());
		if (user != null){
			if (patchUser.getFirstName() != null) user.setFirstName(patchUser.getFirstName());
			if (patchUser.getLastName() != null) user.setLastName(patchUser.getLastName());
			if (patchUser.getEmail() != null) user.setEmail(patchUser.getEmail());
			if (patchUser.getPhone() != null) user.setPhone(patchUser.getPhone());
			user = userService.add(user);
		}
		return user;
	}
	
	@DeleteMapping("/@{username}")
	public User deleteUser(@RequestBody PatchUser patchUser) {
		User user = userService.checkPassword(patchUser.getUsername(), patchUser.getPassword());
		if (user != null){
			user.setDeleted(true);
			user = userService.add(user);
		}
		return user;
	}
}
