package dao;

import domain.ImageWrapper;

/**
 * Interface to outline the operations needed to work with images 
 * @author Noah
 *
 */

public interface IImageDao {

	
	/**
	 * Post an image
	 * @return ImageWrapper containing result
	 */
	
	public ImageWrapper postImage();
	
	
	
	/**
	 * Vote on an image
	 */
	
	public ImageWrapper vote(String url);
	
	
	
	/**
	 * Get a list of the next start-end images from people I follow
	 * @return ImageWrapper containing images
	 */
	
	public ImageWrapper getNextImagesFromFollows(int start, int end);
	
	
	
	/**
	 * Get the next start-end images from all users
	 * @return ImageWrapper containing images
	 */
	
	public ImageWrapper getNextImagesWorld(int start, int end);
}
