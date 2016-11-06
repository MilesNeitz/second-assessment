package com.cooksys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.entity.Hashtag;
import com.cooksys.repository.HashtagRepo;

@Service
public class HashtagService {
	
	private HashtagRepo hashtagRepo;
	
	public HashtagService (HashtagRepo hashtagRepo) {
		this.hashtagRepo = hashtagRepo;
	}
	
	public Hashtag getHashtag(Long id) {
		return hashtagRepo.getOne(id);
	}
	
	public Hashtag addHashtag(Hashtag hashtag) {
		return hashtagRepo.saveAndFlush(hashtag);
	}

	public boolean findLabel(String name) {
		return (hashtagRepo.findByName(name) != null);
	}

	public List<Hashtag> findAll() {
		return hashtagRepo.findAll();
	}
	
	public Hashtag checkHashtag(String name) {
		return hashtagRepo.findByName(name);
	}
	
}