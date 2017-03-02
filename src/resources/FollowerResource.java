package resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ext.Provider;

import dao.FollowRequestDao;
import domain.FollowRequest;
import domain.User;
import service.FollowService;

@Provider
@Path("/followers")
public class FollowerResource implements IFollowerResource {
	
	
	@Override
	public FollowRequest follow(FollowRequest request){
		
		FollowService service = new FollowService(new FollowRequestDao(request));
		return service.follow();
	}
	
	
	
	@Override
	public FollowRequest getAllIFollow(@PathParam ("sender") String username){
		
		User user = new User();
		user.setUsername(username);
		FollowRequest followRequest = new FollowRequest();
		followRequest.setSender(user);
		FollowService service = new FollowService(new FollowRequestDao(followRequest));
		return service.getFollowees();
	}
	
	
	
	@Override
	public FollowRequest getAllFollowMe(@PathParam("sender")String username){
			 	
		User user = new User();
		user.setUsername(username);
		FollowRequest followRequest = new FollowRequest();
		followRequest.setSender(user);
		FollowService service = new FollowService(new FollowRequestDao(followRequest));
		return service.getFollowers();
	}
	
	
	
	@Override
	public FollowRequest getUsersIMayKnow(@PathParam("sender")String username){
		
		User user = new User();
		user.setUsername(username);
		FollowRequest followRequest = new FollowRequest();
		followRequest.setSender(user);
		FollowService service = new FollowService(new FollowRequestDao(followRequest));
		return service.getPotential();
	}
	
	
	
	@Override
	public FollowRequest deleteFollowee(@PathParam("senderAndUserToDelete") String senderAndUserToDelete){
		
		String[] splitStr = senderAndUserToDelete.split("\\s+");
		String sender = splitStr[0];
		String userToDelete = splitStr[1];
		FollowService service = new FollowService(new FollowRequestDao());
		return service.delete(sender, userToDelete);
	}
}
