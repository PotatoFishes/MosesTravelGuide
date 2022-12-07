import java.util.List;

public class User
{
	int id;
	private String username = "", password = "", email = "", location = "";
	List<Integer> friends;
	
	public User() {
		
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", location=" + location + ", friends=" + friends + "]";
	}

	public User(int id,String un,String pw,String em,String l, List<Integer> f) {
		this.id=id;
		username=un;
		password=pw;
		email=em;
		location=l;
		String[] r = null;
		friends=f;
	}
	public User(int id,String un,String pw,String em,String l) {
		this.id=id;
		username=un;
		password=pw;
		email=em;
		location=l;
		String[] r = null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public List<Integer> getFriends() {
		return friends;
	}

	public void setFriends(List<Integer> friends) {
		this.friends = friends;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        return password != null ? password.equals(user.password) : user.password == null;
    }

    @Override
    public int hashCode()
    {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
