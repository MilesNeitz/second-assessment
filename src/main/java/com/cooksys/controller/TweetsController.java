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
import com.cooksys.entity.Hashtag;
import com.cooksys.entity.Tweet;
import com.cooksys.entity.User;
import com.cooksys.service.HashtagService;
import com.cooksys.service.TweetService;
import com.cooksys.service.UserService;

@RestController
@RequestMapping("tweets")
public class TweetsController {
	
	TweetService tweetService;
	
	HashtagService hashtagService;
	
	UserService userService;
	
	public TweetsController(TweetService tweetService, UserService userService, HashtagService hashtagService) {
		this.tweetService = tweetService;
		this.userService = userService;
		this.hashtagService = hashtagService;
	}
	
	@GetMapping()
	public List<Tweet> getTweets() {
		return tweetService.findAllTweets();
	}
	
	@GetMapping("/{id}")
	public Tweet getTweet(@PathVariable Long id) {
		return tweetService.getTweet(id);
	}
	
	@GetMapping("/{id}/tags")
	public List<Hashtag> getTags(@PathVariable Long id) {
		return (tweetService.getTweet(id)).getTaged();
	}
	
	@GetMapping("/{id}/likes")
	public List<User> getLikes(@PathVariable Long id) {
		return (tweetService.getTweet(id)).getLikes();
	}
	
	@GetMapping("/{id}/context")
	public String getContext(@PathVariable Long id) {
		return (tweetService.getTweet(id)).getContent();
	}
	
	@GetMapping("/{id}/replies")
	public List<Tweet> getReplys(@PathVariable Long id) {
		Tweet tweet = tweetService.getTweet(id);
		List<Tweet> replies = tweetService.getInReplyTo(tweet);
		if (replies == null) {
			//give error
		}
		return replies;
	}
	
	@GetMapping("/{id}/reposts")
	public List<Tweet> getReposts(@PathVariable Long id) {
		Tweet tweet = tweetService.getTweet(id);
		List<Tweet> reposts = tweetService.getRepostOf(tweet);
		if (reposts == null) {
			//give error
		}
		return reposts;
	}
	
	@GetMapping("/{id}/mentions")
	public List<User> getMentions(@PathVariable Long id) {
		return (tweetService.getTweet(id)).getMentioned();
	}
	
	@PostMapping("/{id}/like")
	public void postLike(@PathVariable Long id, @RequestBody NewTweet newTweet) {
		
		Tweet tweet = tweetService.getTweet(id);
		User user = userService.checkPassword(newTweet.getUsername(), newTweet.getPassword());
		if (!tweet.getLikes().contains(user)) {
			if (tweet.getLikes() == null) {
				List<User> users = new ArrayList<User>();
				users.add(user);
				tweet.setLike(users);
				tweetService.addTweet(tweet);
			}
			else {
				tweet.addLike(user);
				tweetService.addTweet(tweet);
			}
		}
		else {
			//give error
		}
	}
	
	// case sensitive 
	@PostMapping()
	public Tweet postSimpleTweet(@RequestBody NewTweet newTweet) {
		Tweet tweet = new Tweet();
		
		User author = userService.checkPassword(newTweet.getUsername(), newTweet.getPassword());
		if (author != null) {
			tweet.setContent(newTweet.getContent());
			tweet.setAuthor(author);
			
			tweet = sortContent(newTweet,tweet);
		}
		else {
			//give error
		}
		tweet = tweetService.addTweet(tweet);
		return tweet;
	}
	
	@PostMapping("/{id}/reply")
	public Tweet postReplyTweet(@PathVariable Long id, @RequestBody NewTweet newTweet) {
		
		Tweet tweet = new Tweet();
		
		User author = userService.checkPassword(newTweet.getUsername(), newTweet.getPassword());
		if (author != null) {
			tweet.setContent(newTweet.getContent());
			tweet.setAuthor(author);
			Tweet replyedTweet = tweetService.getTweet(id);
			if (replyedTweet != null) {
				tweet.setInReplyTo(replyedTweet);
			}
			else {
				// give error
			}
			tweet = sortContent(newTweet,tweet);
		}
		else {
			//give error
		}
		tweet = tweetService.addTweet(tweet);
		return tweet;
	}
	
	@PostMapping("/{id}/repost")
	public Tweet postRepostTweet(@PathVariable Long id, @RequestBody NewTweet newTweet) {
		
		Tweet tweet = new Tweet();
		
		User author = userService.checkPassword(newTweet.getUsername(), newTweet.getPassword());
		if (author != null) {
			tweet.setAuthor(author);
			Tweet repostedTweet = tweetService.getTweet(id);
			if (repostedTweet != null) {
				tweet.setRepostOf(repostedTweet);
			}
			else {
				// give error
			}
			tweet = sortContent(newTweet,tweet);
		}
		else {
			// give error
		}
		tweet = tweetService.addTweet(tweet);
		return tweet;
	}
	
	private Tweet sortContent(NewTweet newTweet, Tweet tweet){
		String[] contents = newTweet.getContent().split("\\s+");
		for (String content : contents) {
			// make mentions for each string starting with @
            if (content.substring(0, 1).equals("@")) {
            	User user = userService.getUserByUsername(content.substring(1));
            	
            	if (user == null) {
            		
            	}
            	
            	List<User> Mentions = tweet.getMentioned();
            	
        		if (Mentions == null) {
        			Mentions = new ArrayList<User>();            	
            	}
        		
        		Mentions.add(user);
        		tweet.setMention(Mentions); 	
            }
            
            // make hashtags for each string starting with #
            if (content.substring(0, 1).equals("#")) {
            	Hashtag hashtag = hashtagService.checkHashtag(content);
            	
            	if (hashtag == null) {
            		hashtag = new Hashtag();
            		hashtag.setName(content);
            		hashtagService.addHashtag(hashtag);
            	}
            	
            	List<Hashtag> hashtags = tweet.getTaged();
            	
        		if (hashtags == null) {
        			hashtags = new ArrayList<Hashtag>();            	
            	}
        		
        		hashtags.add(hashtag);
        		tweet.setTaged(hashtags);       	
            }
        }
		return tweet;
	}
}
