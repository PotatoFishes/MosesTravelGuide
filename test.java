import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {

	static final String DB_URL = "jdbc:mysql://mosestravel.cljarowffwyg.us-east-2.rds.amazonaws.com:3306/MosesTravel";
	static final String USER = "admin";
	static final String PASS = "HelloWorld";
	public static void main(String[] args) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT id, eventName, Start, End, Location FROM Events");) {
		         // Extract data from result set
		         while (rs.next()) {
		            // Retrieve by column name
		            System.out.print("ID: " + rs.getInt("id"));
		            System.out.print(", EventName: " + rs.getString("eventName"));
		            System.out.print(", Start: " + rs.getTimestamp("Start"));
		            System.out.print(", End: " + rs.getTimestamp("End"));
		            System.out.println(", Location: " + rs.getString("location"));
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		} 
	}

}
