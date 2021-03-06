package com.cooksys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.Hashtag;

public interface HashtagRepo extends JpaRepository<Hashtag, Long>{

	Hashtag findByName(String name);
	
}
