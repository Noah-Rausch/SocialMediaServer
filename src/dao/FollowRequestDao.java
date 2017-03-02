package dao;

import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import domain.FollowRequest;
import domain.User;
import service.JDBC;
import service.PeopleIMayKnowInterface;

public class FollowRequestDao implements IFollowRequestDao {
	
	private FollowRequest followRequest;
	JDBC db;

	public FollowRequestDao(){
		
		this.db = new JDBC();
	}


	public FollowRequestDao(FollowRequest followRequest){
		
		this.followRequest = followRequest;
		this.db = new JDBC();
	}
	
	
	/**
	 * Have the user follow another user.  Add data contained to SQL db
	 */
	
	@Override
	public FollowRequest addFollow(){
		
		/*
		 * FOLLOWS
		 * | follower | followee |
		 * 
		 */
		
		System.out.println("Adding a follower");
		FollowRequest resultRequest = new FollowRequest(followRequest.getSender(), followRequest.getToBeFollowed());
		String sender = "'" + followRequest.getSender().getUsername() + "'";
		String userToFollow = "'" + followRequest.getToBeFollowed().getUsername() + "'"; 
		
		// First check if the user you want to follow exists in the database as a user.
		String checkIfExists = "SELECT * FROM USERS WHERE username = " + userToFollow;
		ResultSet resultSet = db.selectQuery(checkIfExists);
		try {
			if(resultSet.next()){
				String checkIfFollowing =  "SELECT * FROM FOLLOWS WHERE follower = " + sender + " AND followee = " + userToFollow;
				ResultSet dbResult = db.selectQuery(checkIfFollowing);
				try {
					if(dbResult.next()){
						// The relationship is already in the database.
						resultRequest.setResponseMessage("Already following user");
					}
					
					else{
						// Add the relationship.
						String addFollowStr = "INSERT INTO FOLLOWS " + "VALUES(" + 0 + "," + sender + "," + userToFollow + ")";
						db.putQuery(addFollowStr);
						resultRequest.setResponseMessage("Follow successful");
					}
				} catch (SQLException e) {
					// Error in "FOLLOWS" table operation.
					e.printStackTrace();
				}
			}
			else{
				// The user doesn't even exist.
				resultRequest.setResponseMessage("User doesn't exist");
			}
		} catch (SQLException e1) {
			
			// Error in USERS table operation.
			e1.printStackTrace();
		}
		
		db.cleanUp();
		System.out.println("closing connection to DB");
		return resultRequest;
	}
	
	
	
	@Override
	public FollowRequest getWhoIFollow(){
		
		System.out.println("Getting who I follow");
		FollowRequest resultRequest = new FollowRequest(followRequest.getSender(), followRequest.getToBeFollowed());
		String sender = "'" + followRequest.getSender().getUsername() + "'";
		
		// Get all the users who the sender is following.
		ArrayList<User> whoIFollowList = new ArrayList<>();
		String getWhoIfollowStr = "SELECT * FROM FOLLOWS WHERE follower = " + sender;
		ResultSet dbResult = db.selectQuery(getWhoIfollowStr);
		try {
			while(dbResult.next()){
				User user = new User();
				user.setUsername(dbResult.getString("followee"));
				whoIFollowList.add(user);
			}
		} catch (SQLException e) {
			//Error
			e.printStackTrace();
		}
		
		resultRequest.setUsersIFollow(whoIFollowList);
		db.cleanUp();
		System.out.println("closing connection to DB");
		return resultRequest;
	}
	
	
	
	@Override
	public FollowRequest getMyFollowers(){
		
		FollowRequest resultRequest = new FollowRequest(followRequest.getSender(), followRequest.getToBeFollowed());
		String sender = "'" + followRequest.getSender().getUsername() + "'";
		
		// Get all the users who follow the sender.
		ArrayList<User> followersList = new ArrayList<>();
		String followersStr = "SELECT * FROM FOLLOWS WHERE followee = " + sender;
		ResultSet dbResult = db.selectQuery(followersStr);
		try {
			while(dbResult.next()){
				User user = new User();
				user.setUsername(dbResult.getString("follower"));
				followersList.add(user);
			}
		} catch (SQLException e) {
			//Error
			e.printStackTrace();
		}
		
		resultRequest.setUsersWhoFollowMe(followersList);
		db.cleanUp();
		System.out.println("closing connection to DB");
		return resultRequest;
	}
	
	
	
	
	@Override
	public FollowRequest getUsersIMayKnow(){
		
		FollowRequest resultRequest = new FollowRequest();
		PeopleIMayKnowInterface friendFinder = new PeopleIMayKnowInterface(this.db, 50);
		ArrayList<String> friends = friendFinder.getMyFollowers("'" + followRequest.getSender().getUsername() + "'");
		ArrayList<String> asList = new ArrayList<String>(friendFinder.getFriendsOfFollowers(friends));
		resultRequest.setUsersIMayKnow(asList);
		return resultRequest;
	}
	
	
	
	@Override  
	public FollowRequest deleteUser(String sender, String userToDelete){
		
		FollowRequest resultRequest = new FollowRequest();
		String senderStr = "'" + sender + "'";
		String userToDeleteStr = "'" + userToDelete + "'";
		
		String deleteQueryStr = "DELETE FROM FOLLOWS WHERE follower = " + senderStr + " AND followee = " + userToDeleteStr;
		db.putQuery(deleteQueryStr);
		
		resultRequest.setResponseMessage("No longer following " + userToDelete);
		db.cleanUp();
		return resultRequest;
	}
}
