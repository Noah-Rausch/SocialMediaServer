package domain;

import java.util.ArrayList;


/**
 * Model to represent a follower/followee relationship
 * @author noahr
 *
 */

public class FollowRequest {
	private User sender;
	private User toBeFollowed;
	private ArrayList<User> usersIFollow;
	private ArrayList<User> usersWhoFollowMe;
	private ArrayList<String> usersIMayKnow;
	private String responseMessage;
	
	public FollowRequest(){
		
	}
	
	
	/**
	 * Construct a 'FollowRequest' object, with information needed to create the relationship
	 * @param send User to follow other user
	 * @param toFollow User to be followed by sender
	 */
	
	public FollowRequest(User send, User toFollow){
		this.sender = send;
		this.toBeFollowed = toFollow;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getToBeFollowed() {
		return toBeFollowed;
	}

	public void setToBeFollowed(User toBeFollowed) {
		this.toBeFollowed = toBeFollowed;
	}

	public ArrayList<User> getUsersIFollow() {
		return usersIFollow;
	}

	public void setUsersIFollow(ArrayList<User> usersIFollow) {
		this.usersIFollow = usersIFollow;
	}

	public ArrayList<User> getUsersWhoFollowMe() {
		return usersWhoFollowMe;
	}

	public void setUsersWhoFollowMe(ArrayList<User> usersWhoFollowMe) {
		this.usersWhoFollowMe = usersWhoFollowMe;
	}
	

	public ArrayList<String> getUsersIMayKnow() {
		return usersIMayKnow;
	}

	public void setUsersIMayKnow(ArrayList<String> usersIMayKnow) {
		this.usersIMayKnow = usersIMayKnow;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
}
