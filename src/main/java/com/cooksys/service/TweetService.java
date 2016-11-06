package com.cooksys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.entity.Context;
import com.cooksys.entity.Tweet;
import com.cooksys.repository.ContextRepo;
import com.cooksys.repository.TweetRepo;

@Service
public class TweetService {

	private TweetRepo tweetRepo;
	
	private ContextRepo contextRepo;
	
	public TweetService (TweetRepo tweetRepo, ContextRepo contextRepo) {
		this.tweetRepo = tweetRepo;
		this.contextRepo = contextRepo;
	}
	
	public Tweet getTweet(Long id) {
		return tweetRepo.findById(id);
	}
	
	public Context getContext(Long id) {
		return contextRepo.getOne(id);
	}

	public Tweet addTweet(Tweet tweet) {
		return tweetRepo.saveAndFlush(tweet);
	}
	
	public Context addContext(Context context) {
		return contextRepo.saveAndFlush(context);
	}

	public List<Tweet> findAllTweets() {
		return tweetRepo.findAll();
	}
	

	
}