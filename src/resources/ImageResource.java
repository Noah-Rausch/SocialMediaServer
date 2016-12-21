package resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import classes.ImageModel;
import classes.ImageModelDao;

@Provider
@Path("/images")
public class ImageResource {
	
	// Post in image to the server.
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ImageModel postImage(ImageModel image){
		
		// FOR DEBUGGING
		System.out.println("Image made it to server");
		
		ImageModelDao imageModelDao = new ImageModelDao(image);
		return imageModelDao.postImage();
	}
	
	
	// Get a list of all the images sent to user.
	@GET
	@Path("/{sender}/{instruction}")
	@Produces(MediaType.APPLICATION_JSON)
	public ImageModel getImages(@PathParam("sender") String sender, @PathParam("instruction") String instruction){
		
		ImageModel imageModel = new ImageModel();
		if(instruction.equals("forVote")){
			
			System.out.println("Getting images for vote");
			// If the instruction is to get the images to vote on, just get the images
			// that have been sent by people this user is following.  This only gets
			// the images that have been taken recently and you haven't seen yet.
			ArrayList<String> senderList = new ArrayList<>();
			senderList.add(sender);
			imageModel.setSender(senderList);
			ImageModelDao imageModelDao = new ImageModelDao(imageModel);
			return imageModelDao.getImagesToVote();
		}
		else if(instruction.equals("forCircle")){
			
			System.out.println("Getting images for circle");
			// If the instruction is 'forCircle', we are also getting all the images
			// sent by users we follow, but ones that have been voted on.
			ArrayList<String> senderList = new ArrayList<>();
			senderList.add(sender);
			imageModel.setSender(senderList);
			ImageModelDao imageModelDao = new ImageModelDao(imageModel);
			return imageModelDao.getImagesPeopleIFollow();
		}
		else{
			
			System.out.println("Getting images for world");
			// If the instruction was "forWorld", we are displaying the highest rated images from
			// the database, regardless of who sent them.
			ArrayList<String> senderList = new ArrayList<>();
			senderList.add(sender);
			imageModel.setSender(senderList);
			ImageModelDao imageModelDao = new ImageModelDao(imageModel);
			return imageModelDao.getImagesForWorld();
		}
	}
	
	
	
	// Delete a single image from the database
	@DELETE
	@Path("/{sender}")
	@Produces(MediaType.APPLICATION_JSON)
	public ImageModel deleteImageForUser(@PathParam("sender") String sender){
		
		// FOR COMPILER
		return new ImageModel();
	}
}
