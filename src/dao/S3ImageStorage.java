package dao;


/**
 * Class to handle access to AWS to save an image
 */

import java.io.ByteArrayInputStream;

// Object to deal with the actual communication with s3.  Allows us to upload a file.

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;


public class S3ImageStorage implements IImageStorage {
	
	private String bucketName = "testimagebucketnoah";
	private String keyName;
	private String uploadFileName;
	private byte[] image;
	
	public S3ImageStorage(String f, String k, byte[] im){
		this.keyName = k;
		this.uploadFileName = f;
		this.image = im;
	}
	
	public S3ImageStorage(){
		// Empty constructor
	}
	
	
	/**
	 * Save image to AWS s3 service
	 */
	
	@Override
	public void saveFile() {
	   
        s3client.setRegion(usWest2);
     
		try {
			
			InputStream stream = new ByteArrayInputStream(image);

			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(image.length);
			meta.setContentType("image/png");
			
	        FileOutputStream fileOutputStream;
	        File dummyFile = new File(uploadFileName + ".jpg");
	        
	        // Write the byte array containing the image into a file.
	        try { 
	        	
	        	if(!dummyFile.exists()){
		        	dummyFile.createNewFile();
		        }
	            fileOutputStream = new FileOutputStream(dummyFile); 
	            fileOutputStream.write(image);
	            fileOutputStream.flush();
	            fileOutputStream.close();
	        }
	        catch(Exception e){
	        	
	            	System.out.println("Outputstream error");
	            	System.err.println(e);
	        }
	       
	        // Using the file we just wrote, put it into the S3 bucket, with the key being
	        // the key name we specified in the constructor.
	        s3client.putObject(new PutObjectRequest(
	        		                 bucketName, keyName + ".png", stream, meta));
	        

	        // In case of errors, print to console to debug.
	     } 
		catch (AmazonServiceException ase) {
			
	        System.out.println("Caught an AmazonServiceException, which " +  
	        		"means your request made it " +
	                "to Amazon S3, but was rejected with an error response" +
	                " for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	        System.out.println("Caught an AmazonClientException, which " +
	        		"means the client encountered " +
	                "an internal error while trying to " +
	                "communicate with S3, " +
	                "such as not being able to access the network.");
	        System.out.println("Error Message: " + ace.getMessage());
	    }
	}
}
