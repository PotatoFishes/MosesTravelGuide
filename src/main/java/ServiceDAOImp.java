import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAOImp {

	static final String DB_URL = "jdbc:mysql://mosestravel.cljarowffwyg.us-east-2.rds.amazonaws.com:3306/mosestravel";
	static final String USER = "admin";
	static final String PASS = "HelloWorld";

	public static List<Service> getServices(int[] list) {
		List<Service> li=new ArrayList<Service>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();){
			for(int i:list)
			{
				ResultSet rs = stmt.executeQuery("SELECT Name, price, Location, StartTime, EndTime, capacity FROM Events WHERE id="+i);
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

	public Service getService(int id) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();){
				ResultSet rs = stmt.executeQuery("SELECT Name, price, Location, StartTime, EndTime, capacity FROM Events WHERE id="+id);
				// Extract data from result set
				while (rs.next()) {
					// Retrieve by column name
					Service e=new Service(id,rs.getString("Name"),rs.getDouble("Price"),rs.getTimestamp("StartTIme"),rs.getTimestamp("EndTime"),rs.getInt("capacity"));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}

	public void updateService(Service e) {
		// TODO Auto-generated method stub

	}

	public void deleteService(Service e) {
		// TODO Auto-generated method stub

	}

}
