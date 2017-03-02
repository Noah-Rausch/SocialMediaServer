package service;

import java.sql.*;

// Class that implements the details of connecting to and accessing a database.
public class JDBC {
		
	// URL and driver name.
	final String HOST_NAME = "jdbc:mysql://serverrds.ctebtncelic3.us-west-2.rds.amazonaws.com:3306/ServerRDS";
	final int PORT_NUM = 3306;
	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	// Database credentials
	String username = "noah";
    String password = "Rgrint555";

    // Database functionality variables.
    Connection conn = null;
    Statement stmt = null;

    // Constructor, sets Connection and Statement objects to null.
    public JDBC(){
    	try{

    		// Register JDBC driver
    		Class.forName(JDBC_DRIVER);
    	}
    	catch(Exception e){
    		System.out.println("Error loading driver");
    		System.err.println(e);
    	}
    	try{

    		// Open a connection
    		conn = DriverManager.getConnection(HOST_NAME, username, password);
    		if(conn != null){
    			System.out.println("Connected to database");
    		}
    	}     
    	catch(Exception e){
    		System.out.println("Error connecting to database");
    		System.err.println(e);
    	}
    }

    // Method to create statement.
    public ResultSet selectQuery(String sql){
    	ResultSet rs = null;
    	try{
    		stmt = conn.createStatement();
    		rs = stmt.executeQuery(sql);
    	}
    	catch(Exception e){
    		System.err.println(e);
    	}
    	return rs;
    }

    // Method that that provides INSERT functionality to the DB object.
    public void putQuery(String sql){
    	try{
    		stmt = conn.createStatement();
    		stmt.executeUpdate(sql);
    	}
    	catch(Exception e){
    		System.err.println(e);
    	}
    }

    // method extract the data from a ResultSet.  
    public String processResultSet(ResultSet rs){
    	String resultStr = "";
    	return resultStr;
    	}

    // Close up the connecting.
    public void cleanUp(){
    	try{
    		stmt.close();
    		conn.close();
    	}
    	catch(Exception e){
    		System.err.println(e);
    	}
    }
}