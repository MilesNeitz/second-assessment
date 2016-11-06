package com.cooksys.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Tweet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@OneToOne
	private User author;
	
	@CreationTimestamp
	private Timestamp posted;
	
	private String content;
	
	@OneToOne
	private Tweet inReplyTo;
	
	@OneToOne
	private Tweet repostOf;
	
	@ManyToMany
	private List<Hashtag> hashtags;
	
	@ManyToMany
	private List<User> mentions;
	
	@ManyToMany
	private List<User> likes;

	public List<User> getLikes() {
		return likes;
	}

	public void setLike(List<User> likes) {
		this.likes = likes;
	}
	
	public void addLike(User user) {
		this.likes.add(user);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Timestamp getPosted() {
		return posted;
	}

	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Tweet getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(Tweet inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public Tweet getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}
	
	public List<Hashtag> getTaged() {
		return hashtags;
	}

	public void setTaged(List<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}
	
	public void addTaged(Hashtag hashtag) {
		this.hashtags.add(hashtag);
	}

	public List<User> getMentioned() {
		return mentions;
	}

	public void setMention(List<User> mentions) {
		this.mentions = mentions;
	}

}