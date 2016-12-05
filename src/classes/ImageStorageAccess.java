package classes;


// Object to deal with the actual communication with s3.  Allows us to upload a file.

import java.io.File;
import java.io.FileOutputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

public class ImageStorageAccess {
	
	private String bucketName = "testimagebucketnoah";
	private String keyName;
	private String uploadFileName;
	private byte[] image;
	
	public ImageStorageAccess(String f, String k, byte[] im){
		this.keyName = k;
		this.uploadFileName = f;
		this.image = im;
	}
	
	public ImageStorageAccess(){
		// Empty constructor
	}
	
	
	// Uploads the file contained in this object, given the data fields provided in the constructor.
	public void uploadFile(){
		
	    AWSCredentials credentials = new BasicAWSCredentials("AKIAJJJHRCTK52Y46E3Q", "GSlbWqonZbBSVSVSw5zHqDc/Z+629B5zKzB+nxRh");
		AmazonS3 s3client = new AmazonS3Client(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        s3client.setRegion(usWest2);
     
		try {
	        System.out.println("Uploading a new object to S3 from a file");
	        FileOutputStream fileOutputStream;
	        System.out.println("File name will be: " + uploadFileName); 
	        File dummyFile = new File(uploadFileName);
	        
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
	        		                 bucketName, keyName, dummyFile));
	        

	        // In case of errors, print to console to debug.
	     } catch (AmazonServiceException ase) {
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
