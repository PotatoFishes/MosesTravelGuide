import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAOImp {

	static final String DB_URL = "jdbc:mysql://mosestravel.cljarowffwyg.us-east-2.rds.amazonaws.com:3306/MosesTravel";
	static final String USER = "admin";
	static final String PASS = "HelloWorld";

	public static List<Service> getServices(int[] list) {
		List<Service> li=new ArrayList<Service>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();){
			for(int i:list)
			{
				ResultSet rs = stmt.executeQuery("SELECT Name, price, Location, StartTime, EndTime, capacity FROM Services WHERE id="+i);
				// Extract data from result set
				while (rs.next()) {
					// Retrieve by column name
					Service e=new Service(i,rs.getString("Name"),rs.getDouble("Price"),rs.getTimestamp("StartTIme"),rs.getTimestamp("EndTime"),rs.getInt("capacity"));
					li.add(e);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return li;
	}

	public static Service getService(int id) {
		Service e=null;
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();){
				ResultSet rs = stmt.executeQuery("SELECT name, price, startTime, endTime, capacity FROM Services WHERE id="+id);
				// Extract data from result set
				while (rs.next()) {
					// Retrieve by column name
					e=new Service(id,rs.getString("name"),rs.getDouble("price"),rs.getTimestamp("startTIme"),rs.getTimestamp("endTime"),rs.getInt("capacity"));
				}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} 
		return e;
	}

	public static void updateService(Service e) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT Name, price, StartTime, EndTime, capacity FROM Services WHERE id="+e.ID);) {
		         // Extract data from result set
		         if (rs.next()) {
		            // Retrieve by column name
		        	stmt.executeUpdate("UPDATE Services SET Name='"+e.getName()+"', StartTime='"+e.getStartDate()+"', EndTime='"+e.getEndDate()+"', price="+e.getPrice()+", capacity="+e.getCapacity()+" WHERE id="+e.getID());
		         }
		         else
		         {
		        	 stmt.executeUpdate("INSERT INTO Service (Name, StartTime, End, price, capacity) VALUES('"+e.getName()+"', '"+e.getStartDate()+"', '"+e.getEndDate()+"', "+e.getPrice()+", "+e.getCapacity()+")");
		         }
		      } catch (SQLException ex) {
		         ex.printStackTrace();
		      } 

	}

	public void deleteService(Service e) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT id, eventName, Start, End, Location, notes, usedServices, userid FROM Services WHERE id="+e.ID);) {
		         // Extract data from result set
		         if (rs.next()) {
		            // Retrieve by column name
		        	stmt.executeUpdate("DELETE from Services WHERE id="+e.getID());
		         }
		      } catch (SQLException ex) {
		         ex.printStackTrace();
		      } 

	}

}
