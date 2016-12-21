package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


// Object that will allow me to separate the code for the 'friend-finder' algorithm.
public class PeopleIMayKnowInterface {

	private JDBC db;
	private int numOfResults;
	
	public PeopleIMayKnowInterface(){
		// Empty constructor.
	}
	
	public PeopleIMayKnowInterface(JDBC d, int n){
		this.db = d;
		this.numOfResults = n;
	}

	
	// Get a list of all the current user's followers.
	public ArrayList<String> getMyFollowers(String name){
		
		ArrayList<String> followersList = new ArrayList<>();
		String followersStr = "SELECT * FROM FOLLOWS WHERE followee = " + name;
		ResultSet dbResult = db.selectQuery(followersStr);
		try {
			
			int count = 0;
			while(dbResult.next() && count <= numOfResults){
				
				followersList.add(dbResult.getString("follower"));
			}
		} 
		catch (SQLException e) {
			
			//Error
			e.printStackTrace();
		}
		return followersList;
	}
	
	
	// Given a list of followers, get a list of their followers.  Loop through the list
	// of friends, and get an amount of their friends.
	public Set<String> getFriendsOfFollowers(ArrayList<String> list){
		
		Set<String> friendsOfFriends = new HashSet<>();  
		for(int i = 0; i < numOfResults && i < list.size(); i++){
			
			String friend = "'" + list.get(i) + "'";
			String sqlQueryStr = "SELECT * FROM FOLLOWS WHERE follower = " + friend;
			ResultSet rs = db.selectQuery(sqlQueryStr);
			try {
				
				while(rs.next()){
					
					friendsOfFriends.add(rs.getString("followee"));
				}
			} 
			catch (SQLException e) {
				
				//Error
				e.printStackTrace();
			}
		}
		return friendsOfFriends;
	}

	public JDBC getDb() {
		return db;
	}

	public void setDb(JDBC db) {
		this.db = db;
	}

	public int getNumOfResults() {
		return numOfResults;
	}

	public void setNumOfResults(int numOfResults) {
		this.numOfResults = numOfResults;
	}	
}
