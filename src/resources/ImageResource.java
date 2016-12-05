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
	@Path("/{sender}")
	@Produces(MediaType.APPLICATION_JSON)
	public ImageModel getImages(@PathParam("sender") String sender){
		
		ImageModel imageModel = new ImageModel();
		// "Sender" field of ImageModel requires an arrayList.  Make a new one and set the only
		// element to the same of the sender who wants their photos.
		ArrayList<String> senderList = new ArrayList<>();
		senderList.add(sender);
		imageModel.setSender(senderList);
		ImageModelDao imageModelDao = new ImageModelDao(imageModel);
		return imageModelDao.getImages();
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
