import java.util.List;

public interface UserDAO {

	public List<User> getUsers(int[] uids);
	public User getUser(int uid);
	public void updateUser(User e);
	public void deleteUser(User e);
}
