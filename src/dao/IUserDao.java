package dao;

import domain.User;


/**
 * Interface to outline functionality for dealing with users
 * @author noahr
 *
 */

public interface IUserDao {
	
	
	public String addUser();
	
	public String verifyUser();
	
	public User getUser(String username);
	
	public void removeUser();
}
