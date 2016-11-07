package com.cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.Tweet;
import com.cooksys.entity.User;

public interface TweetRepo extends JpaRepository<Tweet, Long>{
	
	Tweet findById(Long id);

	List<Tweet> findByInReplyTo(Tweet tweet);

	List<Tweet> findByRepostOf(Tweet tweet);

	List<Tweet> findByAuthor(User user);
	
}
