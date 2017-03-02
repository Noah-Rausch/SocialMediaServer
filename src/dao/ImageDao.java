package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Image;
import domain.ImageWrapper;
import service.JDBC;


/**
 * Class to handle the details of saving images.  Has an internal reference to 
 * the ImageWrapper object containing the image to be saved
 * @author noahr
 *
 */

public class ImageDao implements IImageDao{

	ImageWrapper imageWrapper;
	JDBC db;
	
	
	public ImageDao(){
		
		this.db = new JDBC();
	}
	
	
	
	/**
	 * 
	 * @param im ImageWrapper to be operated on
	 */
	
	public ImageDao(ImageWrapper im){
		
		this.db = new JDBC();
		this.imageWrapper = im;
	}
	
	
	
	/**
	 * Post the image contained internally to AWS s3, and save info in AWS MySQL
	 * Return an ImageWrapper object
	 */
	
	@Override
	public ImageWrapper postImage(){
		
		// Set up local image path and keyname for map storage
		ImageWrapper resultWrapper = new ImageWrapper();
		String sender = "'" + imageWrapper.getImageList().get(0).getSender() + "'";
		int randNum = (int) (Math.random() * 100);
		final String key = "" + randNum;
		final String fileName = "C:/Users/noahr/Desktop/testfile.txt";
		
		// Upload the image to s3 using 'ImageStorageAccess' object.
		S3ImageStorage s3Access = new S3ImageStorage(fileName, key, imageWrapper.getImageList().get(0).getImageBytes());
		s3Access.saveFile();
		
		/*
		 * *** IMAGES ***
		 * 
		 * | id | sender | url | votes |
		 */
		
		String imageURL = "'https://s3-us-west-2.amazonaws.com/testimagebucketnoah/" + key + "'"; //+ ".png'";
		String sqlStr = "INSERT INTO IMAGES " + "VALUES(" + 0 + "," + sender + "," + imageURL + "," + 0 + ")";
		db.putQuery(sqlStr);
		
		// Let the client know the result of the operation.
		resultWrapper.setResponseMessage("Image sent");
		System.out.println("Sending response back to client");
		return resultWrapper;
	}
	
	
	
	@Override
	public ImageWrapper vote(String url){
		
		
		// Get the number of votes an image has from the db, and add one to it and put it back.
		
		int votes = 0;
		int id = 0;
		String getVotesStr = "SELECT * FROM IMAGES WHERE url = " + "'" + url + "'";
		System.out.println(url);
		ResultSet rs = db.selectQuery(getVotesStr);
		
		try{
			if(rs.next()){
				
				votes = rs.getInt("votes") + 1;
				id = rs.getInt("id");
				String update = "UPDATE IMAGES SET votes = " + votes + " WHERE id = " + id;
				db.putQuery(update);
			}
		}
		
		catch(Exception e){
			
		}
		
		ImageWrapper result = new ImageWrapper();
		result.setResponseMessage("Vote successful");
		return result;
	}
	
	

	@Override
	public ImageWrapper getNextImagesFromFollows(int start, int end){
		
		ImageWrapper resultWrapper = new ImageWrapper();
		String sender = "'" + imageWrapper.getImageList().get(0).getSender() + "'";
		// Get the top start - end images sorted by votes
		String sqlStr = "SELECT * FROM IMAGES ORDER BY votes DESC LIMIT " + (end-start) + "OFFSET " + start;
		ResultSet rs = db.selectQuery(sqlStr);
		
		try {
			
			ArrayList<Image> pics = new ArrayList<>();
	 
			while(rs.next()){
				
				ArrayList<String> recipient = new ArrayList<>();
				recipient.add(rs.getString("recipient"));
				pics.add(new Image(rs.getInt("id"), rs.getString("sender"), rs.getString("url"), recipient, rs.getInt("votes")));
			}
			
			resultWrapper.setImageList(pics);
			resultWrapper.sortImages();
		} 
		catch (SQLException e) {
			
			// Error
			e.printStackTrace();
		}
		
		return resultWrapper;
	}
	
	
	
	@Override
	public ImageWrapper getNextImagesWorld(int start, int end){
		
		ImageWrapper resultWrapper = new ImageWrapper();
		String sqlStr = "SELECT * FROM IMAGES";
		ResultSet rs = db.selectQuery(sqlStr);
		try {
			
			ArrayList<Image> picList = new ArrayList<>();
			
			// Using the info from the SQL database, create image objects for each image, and sort them
			// by number of votes.
		
			while(rs.next()){
				
				ArrayList<String> recipient = new ArrayList<>();
				recipient.add(rs.getString("recipient"));
				picList.add(new Image(rs.getInt("id"), rs.getString("sender"), rs.getString("url"), recipient, rs.getInt("votes")));
			}
			
			resultWrapper.setImageList(picList);
			resultWrapper.sortImages();
		} 
		
		catch (SQLException e) {
			
			// Error
			e.printStackTrace();
		}
		
		return resultWrapper;
	}
}
