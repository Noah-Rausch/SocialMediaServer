package dao;

import domain.FollowRequest;


/**
 * Interface to outline functionality needed add/remove follow relationship
 * @author noahr
 *
 */

public interface IFollowRequestDao {

	public FollowRequest addFollow();
	
	public FollowRequest getWhoIFollow();
	
	public FollowRequest getMyFollowers();
	
	public FollowRequest deleteUser(String sender, String userToDelete);
	
	public FollowRequest getUsersIMayKnow();
}
