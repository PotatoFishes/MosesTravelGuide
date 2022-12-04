import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventDAOImp{

	static final String DB_URL = "jdbc:mysql://mosestravel.cljarowffwyg.us-east-2.rds.amazonaws.com:3306/MosesTravel";
	static final String USER = "admin";
	static final String PASS = "HelloWorld";
	
	public static List<Event> getEvents(int uid) {
		List<Event> list=new ArrayList<Event>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT id, eventName, Start, End, Location, notes, usedServices FROM Events WHERE uid="+uid);) {
		         // Extract data from result set
		         while (rs.next()) {
		            // Retrieve by column name
		        	Event e=new Event(rs.getInt("id"),rs.getString("eventName"),rs.getTimestamp("Start"),rs.getTimestamp("End"),rs.getString("Location"),rs.getString("notes"),rs.getString("usedServices"), uid);
		        	list.add(e);
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } 
		return list;
	}

	public static Event getEvent(int id) {
		Event e=null;
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT id, eventName, Start, End, Location, notes, usedServices, userid FROM Events WHERE id="+id);) {
		         // Extract data from result set
		         while (rs.next()) {
		            // Retrieve by column name
		        	e=new Event(id,rs.getString("eventName"),rs.getTimestamp("Start"),rs.getTimestamp("End"),rs.getString("Location"),rs.getString("notes"),rs.getString("usedServices"), rs.getInt("userid"));
		         }
		      } catch (SQLException ex) {
		         ex.printStackTrace();
		      } 
		return e;
	}

	public static void updateEvent(Event e) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT id, eventName, Start, End, Location, notes, usedServices, userid FROM Events WHERE id="+e.ID);) {
		         // Extract data from result set
		         if (rs.next()) {
		            // Retrieve by column name
		        	stmt.executeUpdate("UPDATE Events SET eventName='"+e.getName()+"', Start='"+e.getStartDate()+"', End='"+e.getEndDate()+"', location='"+e.getLocation()+"', notes='"+e.getNote()+"', usedServices='"+e.getUsedServices()+"', userid="+e.getUserID()+" WHERE id="+e.getID());
		         }
		         else
		         {
		        	 stmt.executeUpdate("INSERT INTO Events (eventName, Start, End, Location, notes, usedServices, userid) VALUES('"+e.getName()+"', '"+e.getStartDate()+"', '"+e.getEndDate()+"', '"+e.getLocation()+"', '"+e.getNote()+"', '"+e.getUsedServices()+"', "+e.getUserID()+")");
		         }
		      } catch (SQLException ex) {
		         ex.printStackTrace();
		      } 
		
	}

	public static void deleteEvent(Event e) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT id, eventName, Start, End, Location, notes, usedServices, userid FROM Events WHERE id="+e.ID);) {
		         // Extract data from result set
		         if (rs.next()) {
		            // Retrieve by column name
		        	stmt.executeUpdate("DELETE from Event WHERE id="+e.getID());
		         }
		      } catch (SQLException ex) {
		         ex.printStackTrace();
		      } 
		
	}
	
}
