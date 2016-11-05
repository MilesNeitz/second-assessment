package com.cooksys.service;

import java.util.List;

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

	public Tweet addTweet(Tweet tweet) {
		return tweetRepo.saveAndFlush(tweet);
	}
	
	public Hashtag addHashtag(Hashtag hashtag) {
		return hashtagRepo.saveAndFlush(hashtag);
	}
	
	public Context addContext(Context context) {
		return contextRepo.saveAndFlush(context);
	}

	public boolean findLabel(String label) {
		return (hashtagRepo.findByLabel(label) != null);
	}

	public List<Hashtag> findAll() {
		return hashtagRepo.findAll();
	}

	public List<Tweet> findAllTweets() {
		return tweetRepo.findAll();
	}

	public List<Hashtag> findAllHashtags() {
		return hashtagRepo.findAll();
	}
	
	public Hashtag findHashtag(String label) {
		return hashtagRepo.findByLabel(label);
	}
	
}