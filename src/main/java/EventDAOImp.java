import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventDAOImp{

	static final String DB_URL = "jdbc:mysql://mosestravel.cljarowffwyg.us-east-2.rds.amazonaws.com:3306/mosestravel";
	static final String USER = "admin";
	static final String PASS = "HelloWorld";
	
	public List<Event> getEvents(int uid) {
		List<Event> list=new ArrayList<Event>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT id, eventName, Start, End, Location, notes, usedServices FROM Events WHERE uid="+uid);) {
		         // Extract data from result set
		         while (rs.next()) {
		            // Retrieve by column name
		        	Event e=new Event(rs.getInt("id"),rs.getString("eventName"),rs.getTimestamp("Start"),rs.getTimestamp("End"),rs.getString("Location"),rs.getString("notes"),rs.getString("usedServices"));
		        	list.add(e);
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } 
		return list;
	}

	public Event getEvent(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

	public void deleteEvent(Event e) {
		// TODO Auto-generated method stub
		
	}
	
}
