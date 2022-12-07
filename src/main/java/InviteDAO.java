import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InviteDAO {

	static final String DB_URL = "jdbc:mysql://mosestravel.cljarowffwyg.us-east-2.rds.amazonaws.com:3306/MosesTravel";
	static final String USER = "admin";
	static final String PASS = "HelloWorld";
	
	public static List<String> checkInvite(User e)
	{
		List<String> invite=new ArrayList<String>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();){
				ResultSet rs = stmt.executeQuery("SELECT user1, eventid, accepted  FROM Invites WHERE user2="+e.id);
				// Extract data from result set
				while (rs.next()) {
					// Retrieve by column name
					String s=""+rs.getInt("user1")+","+rs.getInt("eventid")+rs.getBoolean("accepted");
					invite.add(s);
				}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} 
		return invite;
	}
	
	public static void acceptInvite(User u, Event e)
	{
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();){
				ResultSet rs = stmt.executeQuery("SELECT user1, eventid, accepted  FROM Invites WHERE user2="+u.id+" and eventid="+e.getID());
				// Extract data from result set
				if (rs.next()) {
					// Retrieve by column name
					stmt.executeUpdate("INSERT INTO Events (eventName, Start, End, Location, notes, usedServices, userid) VALUES('"+e.getName()+"', '"+e.getStartDate()+"', '"+e.getEndDate()+"', '"+e.getLocation()+"', '"+e.getNote()+"', '"+e.getUsedServices()+"', "+u.id+")");
				}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} 
	}
	/*public static void sendInvite(User u, int uid2)
	{
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();){
				ResultSet rs = stmt.executeQuery("SELECT user1, eventid, accepted  FROM Invites WHERE user2="+u.id+" and eventid="+e.getID());
				// Extract data from result set
				if (rs.next()) {
					// Retrieve by column name
					stmt.executeUpdate("INSERT INTO Events (eventName, Start, End, Location, notes, usedServices, userid) VALUES('"+e.getName()+"', '"+e.getStartDate()+"', '"+e.getEndDate()+"', '"+e.getLocation()+"', '"+e.getNote()+"', '"+e.getUsedServices()+"', "+u.id+")");
				}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} 
	}*/
}
