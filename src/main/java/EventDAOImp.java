import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EventDAOImp implements EventDAO{

	static final String DB_URL = "jdbc:mysql://mosestravel.cljarowffwyg.us-east-2.rds.amazonaws.com:3306/mosestravel";
	static final String USER = "admin";
	static final String PASS = "HelloWorld";
	
	@Override
	public List<Event> getEvents(int uid) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT id, eventName, Start, End, Location FROM Events");) {
		         // Extract data from result set
		         while (rs.next()) {
		            // Retrieve by column name
		            System.out.print("ID: " + rs.getInt("id"));
		            System.out.print(", Age: " + rs.getInt("age"));
		            System.out.print(", First: " + rs.getString("first"));
		            System.out.println(", Last: " + rs.getString("last"));
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } 
		return null;
	}

	@Override
	public Event getEvent(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEvent(Event e) {
		// TODO Auto-generated method stub
		
	}
	
}
