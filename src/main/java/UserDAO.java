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
				ResultSet rs = stmt.executeQuery("SELECT password FROM Users WHERE username='"+un+'"');
				// Extract data from result set
				if (rs.next()) {
					// Retrieve by column name
					if(rs.getString("password").equals(pass))
					{
						u=new User(rs.getString("username"))
					}
				}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} 
	}
	
}
