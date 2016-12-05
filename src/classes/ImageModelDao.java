package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// DAO class to handle the details of communicating with mySQL in regards to 
// performing operations on images.
public class ImageModelDao {

	ImageModel imageModel;
	JDBC db;
	
	
	public ImageModelDao(){
		this.db = new JDBC();
	}
	
	
	public ImageModelDao(ImageModel im){
		this.db = new JDBC();
		this.imageModel = im;
	}
	
	
	// Add an image to the database.
	public ImageModel postImage(){
		ImageModel resultImageModel = new ImageModel();
		// Strings need quotes to be used with SQL.
		String sender = "'" + imageModel.getSender().get(0) + "'";
		
		// First we need to add the image itself to S3 storage in AWS.  Once we have the image's
		// URL, we can save it into the SQL database.
		
		final String key = "SecondTest";
		final String fileName = "C:/Users/noahr/Desktop/testfile.txt";
		
		// Upload the image to s3 using our 'ImageStorageAccess' object.  This object deals with the details
		// of actually uploading an image to s3.  
		ImageStorageAccess s3Access = new ImageStorageAccess(fileName, key, this.imageModel.getImageBytes());
		s3Access.uploadFile();
		
		// We need a URL to the image, so we can store it in the SQL db.
		 String imageURL = "'https://testimagebucketnoah.s3.amazonaws.com/" + key  + "'";
		
		// Loop through list of recipients, and add the image to each of them in the database.
		for(String receiver : imageModel.getRecipient()){
			String recipient = "'" + receiver + "'"; 
			
			String sqlStr = "INSERT INTO IMAGES " + "VALUES(" + 0 + "," + imageURL + "," + sender + "," + recipient + "," + 0 + ")";
			db.putQuery(sqlStr);
		}
		
		// Let the client know the result of the operation.
		resultImageModel.setResponseMessage("Image sent");
		System.out.println("Sending response back to client");
		return resultImageModel;
	}
	
	
	// Get a list of all the photos that have been sent to a user.
	public ImageModel getImages(){
		ImageModel resultImageModel = new ImageModel();
		String sender = "'" + imageModel.getSender().get(0) + "'";
		System.out.println("Sender = " + sender);
		String sqlStr = "SELECT * FROM IMAGES WHERE recipient = " + sender;
		ResultSet rs = db.selectQuery(sqlStr);
		try {
			ArrayList<String> urls = new ArrayList<>();
			ArrayList<String> senders = new ArrayList<>();
			// For each entry, add the sender of the image and the url to the field in the model class. 
			while(rs.next()){
				senders.add(rs.getString("sender"));
				urls.add(rs.getString("url"));
			}
			
			
			// ** TESTING ***
			System.out.println("urls: " + urls.toString());
			
			
			// After we extract the list of senders and URLs, we add the list to the model object.
			resultImageModel.setSender(senders);
			resultImageModel.setUrl(urls);
			
		} catch (SQLException e) {
			// Error
			e.printStackTrace();
		}
		
		return resultImageModel;
	}
}
