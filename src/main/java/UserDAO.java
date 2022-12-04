import java.util.HashSet;
import java.util.Set;

public class UserDAO {
	private static Set<User> users = new HashSet<User>();
	
	public UserDAO() {
		
	}
	
	public void load() {
		//load users from this function
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public User findUser(String username) {
		User u = new User();
		for(User user: users) {
			if(user.getUsername().equals(username)) {
				u = user;
			}
		}
		
		return u;
	}
	
	public boolean nameExists(String username) {
		for(User user: users) {
			if(user.getUsername().equals(username)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean passwordExists(String password) {
		for(User user: users) {
			if(user.getPassword().equals(password)) {
				return true;
			}
		}
		
		return false;
	}
}
