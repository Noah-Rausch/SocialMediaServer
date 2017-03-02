package resources;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import dao.UserDao;
import domain.User;
import service.UserService;

@Provider
@Path("/users")
public class UserResource implements IUserResource {

	
	/**
	 * Create a user account
	 * @param user
	 * @return
	 */
	
	@Override
	public User createAccount(User user) {
		
		UserService service = new UserService(new UserDao(user));
		String result = service.add();
		user.setResponseMessage(result);
		return user;
	}

	
	/**
	 * Log the user in by verifying their credentials
	 * @param user
	 * @return
	 */
	
	@Override
	public User loginAccount(User user) {
		
		UserService service = new UserService(new UserDao(user));
		String result = service.verify();
		user.setResponseMessage(result);
		return user;
	}

	
	/**
	 * Get a single user
	 * @param username
	 * @return
	 */
	
	@Override
	public User getUser(String username) {
		
		UserService service = new UserService(new UserDao(username));
		return service.get(username);
	}
}