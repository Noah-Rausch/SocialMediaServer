package service;

import dao.ImageDao;
import domain.ImageWrapper;


/**
 * Class to handle details of operating on images
 * @author noahr
 *
 */

public class ImageService {

	ImageDao dao;
	
	
	public ImageService(ImageDao d){
		
		this.dao = d;
	}
	
	
	public ImageWrapper post(){
		
		return dao.postImage();
	}
	
	
	public ImageWrapper getNext(int start, int end){
		
		return dao.getNextImages(start, end);
	}
	
	
	public ImageWrapper getAll(){
		
		return dao.getImagesForWorld();
	}
}
