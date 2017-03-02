package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.FollowRequest;


/**
 * Interface to outline functionality required of follow/followee relationships
 * @author noahr
 *
 */

public interface IFollowerResource {

	
	/**
	 * Have a user follow another user
	 * @param request contains data to save the user relationship
	 * @return
	 */
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public FollowRequest follow(FollowRequest request);
	
	
	/**
	 * Get all users a user follows
	 * @param username
	 * @return
	 */
	
	@GET
	@Path("/{sender}/followees")
	@Produces(MediaType.APPLICATION_JSON)
	public FollowRequest getAllIFollow(@PathParam ("sender") String username);
	
	
	/**
	 * Get all users who follow this user
	 * @param username
	 * @return
	 */
	
	@GET
	@Path("/{sender}/followers")
	@Produces(MediaType.APPLICATION_JSON)
	public FollowRequest getAllFollowMe(@PathParam("sender")String username);
	
	
	/**
	 * Get users that this user may know
	 * @param username
	 * @return
	 */
	
	@GET
	@Path("/{sender}/potential")
	@Produces(MediaType.APPLICATION_JSON)
	public FollowRequest getUsersIMayKnow(@PathParam("sender")String username);
	
	
	/**
	 * Delete a specific person this user follows
	 * @param senderAndUserToDelete
	 * @return
	 */
	
	@DELETE
	@Path("{senderAndUserToDelete}")
	@Produces(MediaType.APPLICATION_JSON)
	public FollowRequest deleteFollowee(@PathParam("senderAndUserToDelete") String senderAndUserToDelete);
}
