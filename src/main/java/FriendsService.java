import java.util.HashSet;
import java.util.Set;

public class FriendsService {
	static public Set<String[]> getFriendsAccess(){
		User u = UserLoginService.getUser();
		Set<String[]> out = new HashSet<String[]>();
		Set<Integer> uids = UserDAO.getFollowers(UserLoginService.getUser());
		for(Integer i: uids) {
			out.add(new String[] {"", i.toString(), " X "});
		}
		return out;
	}
	static public void addPermission(Integer u) {
		UserDAO.addFollow(UserLoginService.getUser(), u);
	}
	static public void removePermission(Integer u) {
		
	}
}
