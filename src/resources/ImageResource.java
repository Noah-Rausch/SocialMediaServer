package resources;

import java.util.ArrayList;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import dao.ImageDao;
import domain.Image;
import domain.ImageWrapper;
import service.ImageService;

@Provider
@Path("/images")
public class ImageResource implements IImageResource {
	
	
	@Override
	public ImageWrapper postImage(ImageWrapper image) {

		ImageService service = new ImageService(new ImageDao(image));
		return service.post();
	}



	@Override
	public ImageWrapper voteOnImage(ImageWrapper image) {

		ImageDao imageDao = new ImageDao();
		return imageDao.vote(image.getImageList().get(0).getUrl());
	}



	@Override
	public ImageWrapper getImages(String sender, String instruction) {

		ImageWrapper imageWrapper = new ImageWrapper();
		Image image = new Image();
		image.setSender(sender);
		ArrayList<Image> pics = new ArrayList<>();
		pics.add(image);
		imageWrapper.setImageList(pics);
		ImageDao imageDao = new ImageDao(imageWrapper);
		
		if(instruction.equals("forVote")){
			
			System.out.println("Getting images for vote");
			imageWrapper.setImageList(pics);
			return imageDao.getImagesToVote();
		}
		else if(instruction.equals("forCircle")){
			
			System.out.println("Getting images for circle");
			return imageDao.getImagesPeopleIFollow();
		}
		else{
			
			System.out.println("Getting images for world");
			return imageDao.getImagesForWorld();
		}
	}



	@Override
	public ImageWrapper deleteImageForUser(String sender) {

		return new ImageWrapper();
	}
}
