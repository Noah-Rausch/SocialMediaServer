package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import classes.User;
import classes.UserDao;

@Provider
@Path("/users")
public class UserResource {

	// Given the User sent from the client to be added to persistence, Return
	// to them the result of the transaction.  Either username/email is taken,
	// or their account was created.
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User createAccount(User user) {
		UserDao userDao = new UserDao(user);
		String result = userDao.addUser();
		user.setResponseMessage(result);
		return user;
	}

	// When the client sends a user object to login, a DAO is created and checks
	// if the user entered the correct credentials in the database, or if the user
	// exists at all.
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User loginAccount(User user) {
		UserDao userDao = new UserDao(user);
		String result = userDao.verifyUser();
		user.setResponseMessage(result);
		return user;
	}

	// Get a single user.  If found, sends back the user stating it was found,
	// Otherwise, sends an empty User object back stating it was not found.
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(String username) {
		UserDao userDao = new UserDao(username);
		User user = userDao.getUser(username);
		return user;
	}
}