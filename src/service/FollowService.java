package service;

import dao.FollowRequestDao;
import domain.FollowRequest;


/**
 * Class to handle details of working with FollowRequest objects
 * @author noahr
 *
 */

public class FollowService {

	FollowRequestDao dao;
	
	public FollowService(FollowRequestDao fr){
		
		this.dao = fr;
	}
	
	
	
	public FollowRequest follow(){
		
		return this.dao.addFollow();
	}
	
	
	
	public FollowRequest getFollowees(){
		
		return this.dao.getWhoIFollow();
	}
	
	
	
	public FollowRequest getFollowers(){
		
		return this.dao.getMyFollowers();
	}
	
	
	
	public FollowRequest getPotential(){
		
		return this.dao.getUsersIMayKnow();
	}
	
	
	
	public FollowRequest delete(String s, String d){
		
		return this.dao.deleteUser(s, d);
	}
}
