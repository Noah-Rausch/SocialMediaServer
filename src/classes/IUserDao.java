package classes;

//An interface that outlines the methods that a User object would utilize to
//interact with a database.
public interface IUserDao {
	public String addUser();
	public String verifyUser();
	public User getUser(String username);
	public void removeUser();
}
