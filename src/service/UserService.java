package service;

import dao.UserDao;
import domain.User;


/**
 * Class to handle details of operating on User objects
 * @author noahr
 *
 */

public class UserService {

	UserDao dao;
	
	
	public UserService(UserDao user){
		
		this.dao = user;
	}
	
	
	
	/**
	 * Save a new user using DAO contained within
	 * @return
	 */
	
	public String add(){
		
		return dao.addUser();
	}
	
	
	
	/**
	 * Verify a user's credentials
	 * @return
	 */
	
	public String verify(){
		
		return dao.verifyUser();
	}
	
	
	
	/**
	 * Get a user
	 * @param name
	 * @return
	 */
	
	public User get(String name){
		
		return dao.getUser(name);
	}
}
