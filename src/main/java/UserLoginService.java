
public class UserLoginService {
	static private User user;
	static {
		user=null;
	}
	public static User getUser() {
		return user;
	}
	public static User validateUser(String username, String password) {
		User u = UserDAO.checkPassword(username, password);
		if(u != null) {
			user = u;
		}
		System.out.println(u);
		return u;
	}
	public static void updateUser(User u) {
		if(u != null) {
			UserDAO.updateUser(u);
			user=u;
		}
	}
	public static void deleteUser() {
		if(user != null) {
			UserDAO.deleteUser(user);
		}
	}
}
