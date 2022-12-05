import java.util.HashSet;
import java.util.Set;

public class FriendsService {
	static public Set<String[]> getFriendsAccess(){
		User u = UserLoginService.getUser();
		Set<String[]> out = new HashSet<String[]>();
		Set<Follower> uids = UserDAO.getFollowers(u);
		for(Follower i: uids) {
			out.add(new String[] {i.getName(), i.getId().toString(), " X "});
		}
		return out;
	}
	static public void addPermission(Integer u) {
		UserDAO.addFollow(UserLoginService.getUser(), u);
	}
	static public void removePermission(Integer u) {
		UserDAO.removeFollower(UserLoginService.getUser(), u);
	}
	static public Set<String[]> accessFriends(){
		Set<String[]> out = new HashSet<String[]>();
		User u = UserLoginService.getUser();
		Set<UserInfo> followed = UserDAO.getFollowed(u);
		for(UserInfo ui: followed) {
			out.add(new String[] {ui.getName(), ui.getEmail(), ui.getLocation(), ui.getId().toString()});
		}
		return out;
	}
}
