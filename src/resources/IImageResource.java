package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.ImageWrapper;

/**
 * Interface to outline functionality needed to work with images
 * @author noahr
 *
 */

public interface IImageResource {

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ImageWrapper postImage(ImageWrapper image);
	
	
	@POST
	@Path("/vote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ImageWrapper voteOnImage(ImageWrapper image);
	
	
	@GET
	@Path("/{sender}/{instruction}")
	@Produces(MediaType.APPLICATION_JSON)
	public ImageWrapper getImages(@PathParam("sender") String sender, @PathParam("instruction") String instruction);
	
	
	@DELETE
	@Path("/{sender}")
	@Produces(MediaType.APPLICATION_JSON)
	public ImageWrapper deleteImageForUser(@PathParam("sender") String sender);
}
