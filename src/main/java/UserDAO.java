import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
				ResultSet rs = stmt.executeQuery("SELECT ID, username, password, location, email, friends FROM Users WHERE username='"+un+"'");
				// Extract data from result set
				if (rs.next()) {
					// Retrieve by column name
					if(rs.getString("password").equals(pass))
					{
						u=new User(rs.getInt("ID"),rs.getString("username"),rs.getString("password"),rs.getString("location"),rs.getString("email"),rs.getString("friends"));
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
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT username, password, location, email FROM Users WHERE username="+e.getUsername());) {
		         // Extract data from result set
		         if (rs.next()) {
		            // Retrieve by column name
		        	stmt.executeUpdate("UPDATE Users SET userame='"+e.getUsername()+"', password='"+e.getPassword()+"', location='"+e.getLocation()+"', location='"+e.getLocation()+"', email='"+e.getEmail()+" WHERE id="+e.id);
		         }
		         else
		         {
		        	 stmt.executeUpdate("INSERT INTO Users (username, password, location, email) VALUES('"+e.getUsername()+"', '"+e.getPassword()+"', '"+e.getLocation()+"',"+e.getEmail()+")");
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
	
}
