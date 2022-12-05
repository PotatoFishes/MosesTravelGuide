import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

	static final String DB_URL = "jdbc:mysql://mosestravel.cljarowffwyg.us-east-2.rds.amazonaws.com:3306/MosesTravel";
	static final String USER = "admin";
	static final String PASS = "HelloWorld";
	
	public static User checkPassword(String un, String pass)
	{
		User u=null;
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();){
				ResultSet rs = stmt.executeQuery("SELECT ID, username, password, location, email FROM Users WHERE username='"+un+"'");
				// Extract data from result set
				if (rs.next()) {
					// Retrieve by column name
					if(rs.getString("password").equals(pass))
					{
						List<Integer> follow=new ArrayList<Integer>();
						u=new User(rs.getInt("ID"),rs.getString("username"),rs.getString("password"),rs.getString("location"),rs.getString("email"),follow);
						ResultSet rs2 = stmt.executeQuery("SELECT user2 FROM Follow WHERE user1="+rs.getInt("ID")+"");
						while(rs2.next())
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
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement stmt = conn.prepareStatement("SELECT username, password, location, email FROM Users WHERE username=?");
				PreparedStatement updateStatement = conn.prepareStatement("UPDATE Users SET userame=?, password=?, location=?, email=? WHERE id=?");
				PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO Users (username, password, location, email) VALUES(?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS)) {
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
		        	//stmt.executeUpdate("UPDATE Users SET userame='"+e.getUsername()+"', password='"+e.getPassword()+"', location='"+e.getLocation()+"', location='"+e.getLocation()+"', email='"+e.getEmail()+" WHERE id="+e.id);
		         }
		         else
		         {
		        	 insertStatement.setString(1, e.getUsername());
		        	 insertStatement.setString(2, e.getPassword());
		        	 insertStatement.setString(3, e.getLocation());
		        	 insertStatement.setString(4, e.getEmail());
		        	 insertStatement.executeUpdate();
		        	 try(ResultSet generatedKeys = insertStatement.getGeneratedKeys()){
		        		 if (generatedKeys.next()) {
		                     e.id = generatedKeys.getInt(1);
		                 }
		                 else {
		                     throw new SQLException("Creating user failed, no ID obtained.");
		                 }
		        	 }
		         }
		      } catch (SQLException ex) {
		         ex.printStackTrace();
		      } 
		
	}
	
	public static void deleteUser(User e) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT username, password, location, email FROM Users WHERE username="+e.getUsername());) {
		         // Extract data from result set
		         if (rs.next()) {
		            // Retrieve by column name
		        	stmt.executeUpdate("DELETE from Users WHERE username="+e.getUsername());
		         }
		      } catch (SQLException ex) {
		         ex.printStackTrace();
		      } 
		
	}
	
	public static void addFollow(User e,int uid) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT user2 FROM Follow WHERE user1="+e.id);) {
		         // Extract data from result set
		         if (!rs.next()) {
		            // Retrieve by column name
		        	 stmt.executeUpdate("INSERT INFO Follow (user1, user2) VALUES("+e.id+","+uid+")");
		         }
		      } catch (SQLException ex) {
		         ex.printStackTrace();
		      } 
		
	}
	
}
