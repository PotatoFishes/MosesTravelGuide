
public class UserLoginService {
	static private Integer userID;
	static private String userName;
	static private String userPassword;
	static {
		userID=null;
		userName=null;
		userPassword=null;
	}
	public static Integer getUserID() {
		return userID;
	}
	public static void setUserID(Integer userID) {
		UserLoginService.userID = userID;
	}
	public static String getUserName() {
		return userName;
	}
	public static void setUserName(String userName) {
		UserLoginService.userName = userName;
	}
	public static String getUserPassword() {
		return userPassword;
	}
	public static void setUserPassword(String userPassword) {
		UserLoginService.userPassword = userPassword;
	}
}
