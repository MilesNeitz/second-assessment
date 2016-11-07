package com.cooksys.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.cooksys.service.TweetService;

@Entity
public class Context {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Tweet target;
	
	@OneToMany
	private List<Tweet> before;
	
	@OneToMany
	private List<Tweet> after;

	
	public List<Tweet> getBefore() {
		this.before = getBefores(this.target, this.before);
		return this.before;
	}

	private List<Tweet> getBefores(Tweet target, List<Tweet> before) {
		// if this is a repost add the original to the before list and check again with the original as the target
		if (target.getRepostOf() != null) before.addAll(getBefores(target.getRepostOf(), before));
		// same as repost but with reply
		if (target.getInReplyTo() != null) before.addAll(getBefores(target.getInReplyTo(), before));
		return before;
	}

	public void setBefore(List<Tweet> before) {
		this.before = before;
	}

	public List<Tweet> getAfter(TweetService tweetService) {
		this.after = getAfters(this.target, this.after, tweetService);
		return this.after;
	}

	private List<Tweet> getAfters(Tweet target, List<Tweet> after, TweetService tweetService) {
		List<Tweet> replies = tweetService.getInReplyTo(target);
		List<Tweet> reposts = tweetService.getRepostOf(target);
		if (replies != null) for (Tweet reply : replies) after.addAll(getAfters(reply, after, tweetService));
		if (reposts != null) for (Tweet repost : reposts) after.addAll(getAfters(repost, after, tweetService));
		return after;
	}

	public void setAfter(List<Tweet> after) {
		this.after = after;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tweet getTarget() {
		return target;
	}

	public void setTarget(Tweet target) {
		this.target = target;
	}
	
}