import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PreferenceDAO {

	static final String DB_URL = "jdbc:mysql://mosestravel.cljarowffwyg.us-east-2.rds.amazonaws.com:3306/MosesTravel";
	static final String USER = "admin";
	static final String PASS = "HelloWorld";
	
	public static void updatePreference(preferences e) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT notification, private, gender userid FROM Preferences WHERE uid="+e.getUid());) {
		         // Extract data from result set
			 if (rs.next()) {
				// Retrieve by column name
				 System.out.println("Preforming Update on Event");
				stmt.executeUpdate("UPDATE Preferences SET notification="+e.isNoti()+", private="+e.isPriv()+", gender='"+e.getGender()+"' WHERE id="+e.getUid());
			 }
			 else
			 {
				 System.out.println("Inserting new Preference");
				 stmt.executeUpdate("INSERT INTO Preferences (notification, private, gender) VALUES('"+e.isNoti()+"', '"+e.isPriv()+"', '"+e.getGender()+"')");
			 }
		  } catch (SQLException ex) {
			 ex.printStackTrace();
		  }
	}
	public static preferences getPreference(int uid) {
		preferences p=null;
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT notification, private, gender userid FROM Preferences WHERE uid="+uid);) {
		         // Extract data from result set
			 while (rs.next()) {
				// Retrieve by column name
				p=new preferences(uid,rs.getBoolean("notification"),rs.getBoolean("private"),rs.getString("gender"));
			 }
		  } catch (SQLException ex) {
			 ex.printStackTrace();
		  }
		return p;
	}
}
