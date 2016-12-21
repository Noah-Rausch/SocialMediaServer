package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import classes.FollowRequest;
import classes.FollowRequestDao;
import classes.User;

@Provider
@Path("/followers")
public class FollowerResource {
	
	
	// Follow another user.  
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public FollowRequest follow(FollowRequest request){
		
		FollowRequestDao dao = new FollowRequestDao(request);
		return dao.addFollow();
	}
	
	
	// Get a list of all the people a user follows.
	@GET
	@Path("/{sender}/followees")
	@Produces(MediaType.APPLICATION_JSON)
	public FollowRequest getAllIFollow(@PathParam ("sender") String username){
		 		
		User user = new User();
		user.setUsername(username);
		FollowRequest followRequest = new FollowRequest();
		followRequest.setSender(user);
		FollowRequestDao dao = new FollowRequestDao(followRequest);
		return dao.getWhoIFollow();
	}
	
	
	// Get a list of all the people that follow a user.
	@GET
	@Path("/{sender}/followers")
	@Produces(MediaType.APPLICATION_JSON)
	public FollowRequest getAllFollowMe(@PathParam("sender")String username){
			 	
		User user = new User();
		user.setUsername(username);
		FollowRequest followRequest = new FollowRequest();
		followRequest.setSender(user);
		FollowRequestDao dao = new FollowRequestDao(followRequest);
		return dao.getMyFollowers();
	}
	
	
	// Get friends of friends.
	@GET
	@Path("/{sender}/potential")
	@Produces(MediaType.APPLICATION_JSON)
	public FollowRequest getUsersIMayKnow(@PathParam("sender")String username){
		
		System.out.println("Reached resource");
		
		User user = new User();
		user.setUsername(username);
		FollowRequest followRequest = new FollowRequest();
		followRequest.setSender(user);
		FollowRequestDao dao = new FollowRequestDao(followRequest);
		return dao.getUsersIMayKnow();
	}
	
	
	// Delete a person the current user is following from their list.
	@DELETE
	@Path("{senderAndUserToDelete}")
	@Produces(MediaType.APPLICATION_JSON)
	public FollowRequest deleteFollowee(@PathParam("senderAndUserToDelete") String senderAndUserToDelete){
		
		// The current user and the user they want to stop following are received as one string, with a space separated them. 
		String[] splitStr = senderAndUserToDelete.split("\\s+");
		String sender = splitStr[0];
		String userToDelete = splitStr[1];
		FollowRequestDao deleteDao = new FollowRequestDao();
		return deleteDao.deleteUser(sender, userToDelete);
	}

}
