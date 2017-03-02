package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.User;
import service.JDBC;


/**
 * Object to handle the details of saving/retrieving users from the database
 * @author noahr
 *
 */


public class UserDao implements IUserDao{
	JDBC db;
	User user;
	String usernameToSearch;

	public UserDao (User u){
		
		this.db = new JDBC();
		this.user = u;
	}

	public UserDao(String un){
		
		this.db = new JDBC();
		this.usernameToSearch = un;
	}


	
	/**
	 * Add a new user to the db
	 */
	
	@Override
	public String addUser() {
		
		String createAccResult = "";
		String usernameWithQuotes = "'" + user.getUsername() + "'";
		String emailWithQuotes = "'" + user.getEmail() + "'";
		String passwordWithQuotes = "'" + user.getPassword() + "'";
		String queryStr = "SELECT * FROM USERS WHERE username = " + usernameWithQuotes;

		ResultSet rs = db.selectQuery(queryStr);
		try {
			
			if(rs != null && rs.next()){
				
				createAccResult = "username taken";
			}
		}
        catch (SQLException e) {
        	
        	e.printStackTrace();
        }

		queryStr = "SELECT * FROM USERS WHERE email = " + emailWithQuotes;
		rs = db.selectQuery(queryStr);
		try {
			
			if(rs != null && rs.next()){
				
				if(createAccResult.equalsIgnoreCase("")){
					
					createAccResult = "email taken";
				}
				else{
					createAccResult = "email and " + createAccResult;
				}
			}
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
        
		
		// Let the client know which entries are already in use.  The string "createAccResult should
		// have been appended the info needed to let the user know.
		
        if(createAccResult.equalsIgnoreCase("")){
        	
        	String sqlStr = "INSERT INTO USERS " + "VALUES(" + 0 + "," + usernameWithQuotes + "," + emailWithQuotes + "," + passwordWithQuotes + ")";
        	db.putQuery(sqlStr);
        	System.out.println("Account created");
        	return "Account created";
        }

        db.cleanUp();
        return createAccResult;
	}
	
	
	
	/**
	 * Get a specific user from the db
	 */
	
	@Override
	public User getUser(String username) {
		
		User userToReturn = new User();
		String usernameWithQuotes = "'" + username + "'";
		String selectQuery = "SELECT * FROM USERS WHERE username = " + usernameWithQuotes;
		ResultSet rs = db.selectQuery(selectQuery);
		try{
			
			if(rs.next()){
				
				userToReturn.setUsername(username);
				userToReturn.setEmail(rs.getString("email"));
				userToReturn.setResponseMessage("found");
			}
			else{
				
				userToReturn.setResponseMessage("not found");
			}
		}
		catch(Exception e){

		}

		return userToReturn;
	}

	@Override
	public void removeUser() {
		
		// TODO Auto-generated method stub
	}

	
	
	/**
	 * Verify the credentials of a user
	 */
	
	@Override
	public String verifyUser() {
		
		System.out.println("Verifying User");
		String usernameWithQuotes = "'" + user.getUsername() + "'";
		String queryStr = "SELECT * FROM USERS WHERE username = " + usernameWithQuotes;
        ResultSet rs = db.selectQuery(queryStr);

        // If the the username is found, then either the password is correct or not. 
        // Otherwise, the user doesn't even exist.
        
        String result = "User doesn't exist";
        try {
        	
        	if(rs.next()){
        		
        		String passFromDB = rs.getString("password");
        		if(passFromDB.equals(user.getPassword())){
        			
        			// Password matches the one inputed.
        			result = "Login Successful";
        		}
        		else{
        			
        			result = "Incorrect Password";
        		}
        	}
        }
        catch (SQLException e) {
        	
        	e.printStackTrace();
        }
        
        db.cleanUp();
        return result;
	}
}
	