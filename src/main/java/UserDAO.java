import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDAO {

	static final String DB_URL = "jdbc:mysql://mosestravel.cljarowffwyg.us-east-2.rds.amazonaws.com:3306/MosesTravel";
	static final String USER = "admin";
	static final String PASS = "HelloWorld";

	public static boolean checkExists(int userID) {
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt
					.executeQuery("SELECT ID, username, password, location, email FROM Users WHERE ID=" + userID);
			// Extract data from result set
			if (rs.next()) {
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static User getUser(String un) {
		User u = null;
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt
					.executeQuery("SELECT ID, username, password, location, email FROM Users WHERE username='" + un +"'");
			// Extract data from result set
			if (rs.next()) {
				u=new User(rs.getInt("ID"),un,rs.getString("password"),rs.getString("email"),rs.getString("location"));
				return u;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static User checkPassword(String un, String pass) {
		User u = null;
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, username, password, location, email FROM Users WHERE username='" + un + "'");
			// Extract data from result set
			if (rs.next()) {
				// Retrieve by column name
				if (rs.getString("password").equals(pass)) {
					List<Integer> follow = new ArrayList<Integer>();
					u = new User(rs.getInt("ID"), rs.getString("username"), rs.getString("password"),
							rs.getString("location"), rs.getString("email"), follow);
					ResultSet rs2 = stmt.executeQuery("SELECT user2 FROM Follow WHERE user1=" + rs.getInt("ID") + "");
					while (rs2.next())
						follow.add(rs2.getInt("user2"));
					u.setFriends(follow);
					return u;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return u;
	}

	public static void updateUser(User e) {
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

				PreparedStatement stmt = conn
						.prepareStatement("SELECT username, password, location, email FROM Users WHERE username=?");
				PreparedStatement updateStatement = conn
						.prepareStatement("UPDATE Users SET username=?, password=?, location=?, email=? WHERE id=?");
				PreparedStatement insertStatement = conn.prepareStatement(
						"INSERT INTO Users (username, password, location, email) VALUES(?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, e.getUsername());
			ResultSet rs = stmt.executeQuery();

			// Extract data from result set
			if (rs.next()) {
				updateStatement.setString(1, e.getUsername());
				updateStatement.setString(2, e.getPassword());
				updateStatement.setString(3, e.getLocation());
				updateStatement.setString(4, e.getEmail());
				updateStatement.setInt(5, e.id);
				updateStatement.executeUpdate();
				// Retrieve by column name
				// stmt.executeUpdate("UPDATE Users SET username='"+e.getUsername()+"',
				// password='"+e.getPassword()+"', location='"+e.getLocation()+"',
				// location='"+e.getLocation()+"', email='"+e.getEmail()+" WHERE id="+e.id);
			} else {
				insertStatement.setString(1, e.getUsername());
				insertStatement.setString(2, e.getPassword());
				insertStatement.setString(3, e.getLocation());
				insertStatement.setString(4, e.getEmail());
				insertStatement.executeUpdate();
				try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						e.id = generatedKeys.getInt(1);
					} else {
						throw new SQLException("Creating user failed, no ID obtained.");
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public static void deleteUser(User e) {
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();) {
			// Extract data from result set
				stmt.executeUpdate("DELETE from Users WHERE id =" + e.id);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addFollow(User e, int uid) {
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT user2 FROM Follow WHERE user1=" + e.id + " AND user2=" + uid);) {
			// Extract data from result set
			if (!rs.next()) {
				// Retrieve by column name
				stmt.executeUpdate("INSERT INTO Follow (user1, user2) VALUES(" + e.id + "," + uid + ")");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public static Set<Friend> getFollowers(User u) {
		Set<Integer> out = new HashSet<Integer>();
		Set<Friend> friend = new HashSet<Friend>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT user2 FROM Follow WHERE user1=" + u.id);) {
			// Extract data from result set
			while (rs.next()) {
				int i = rs.getInt(1);
				out.add(i);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		for (Integer i : out) {
			Friend f = new Friend();
			f.setId(i);
			try (Connection nameConn = DriverManager.getConnection(DB_URL, USER, PASS);
					Statement nameStmt = nameConn.createStatement();
					ResultSet nameRs = nameStmt.executeQuery("SELECT username FROM Users WHERE id=" + i);) {
				if (nameRs.next()) {
					f.setName(nameRs.getString(1));
				} else {
					f.setName("");
				}
				nameRs.close();
			} catch (SQLException ex) {
				f.setName("");
			}
			friend.add(f);
		}
		return friend;
	}

	public static void removeFollower(User u, int uid) {
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();) {
			stmt.execute("DELETE FROM Follow WHERE user1=" + u.id + " AND user2=" + uid);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static Set<UserInfo> getFollowed(User u){
		Set<Integer> ids = new HashSet<Integer>();
		Set<UserInfo> uis = new HashSet<UserInfo>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT user1 FROM Follow WHERE user2=" + u.id);) {
			// Extract data from result set
			while (rs.next()) {
				int i = rs.getInt(1);
				ids.add(i);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		for(Integer i: ids) {
			UserInfo f = new UserInfo();
			f.setId(i);
			try (Connection nameConn = DriverManager.getConnection(DB_URL, USER, PASS);
					Statement nameStmt = nameConn.createStatement();
					ResultSet nameRs = nameStmt.executeQuery("SELECT username, location, email FROM Users WHERE id=" + i);) {
				if (nameRs.next()) {
					f.setName(nameRs.getString(1));
					f.setLocation(nameRs.getString(2));
					f.setEmail(nameRs.getString(3));
				} else {
					f.setName("");
					f.setLocation("");
					f.setEmail("");
				}
				nameRs.close();
			} catch (SQLException ex) {
				f.setName("");
				f.setLocation("");
				f.setEmail("");
				ex.printStackTrace();
			}
			System.out.println(f.toString());
			uis.add(f);
		}
		return uis;
	}

}
