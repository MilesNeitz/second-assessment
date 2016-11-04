package com.cooksys.service;

import org.springframework.stereotype.Service;

import com.cooksys.entity.Context;
import com.cooksys.entity.Hashtag;
import com.cooksys.entity.Tweet;
import com.cooksys.repository.ContextRepo;
import com.cooksys.repository.HashtagRepo;
import com.cooksys.repository.TweetRepo;

@Service
public class TweetService {

	private TweetRepo tweetRepo;
	
	private HashtagRepo hashtagRepo;
	
	private ContextRepo contextRepo;
	
	public TweetService (TweetRepo tweetRepo, HashtagRepo hashtagRepo, ContextRepo contextRepo) {
		this.tweetRepo = tweetRepo;
	}
	
	public Tweet getTweet(Long id) {
		return tweetRepo.getOne(id);
	}
	
	public Hashtag getHashtag(Long id) {
		return hashtagRepo.getOne(id);
	}
	
	public Context getContext(Long id) {
		return contextRepo.getOne(id);
	}

	public void addTweet(Tweet tweet) {
		tweetRepo.saveAndFlush(tweet);
	}
	
	public void addHashtag(Hashtag hashtag) {
		hashtagRepo.saveAndFlush(hashtag);
	}
	
	public void addContext(Context context) {
		contextRepo.saveAndFlush(context);
	}
	
}