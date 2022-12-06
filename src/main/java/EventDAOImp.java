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
	
	public static List<Event> getEvents() {
		int uid;
		List<Event> list=new ArrayList<Event>();
		if(UserLoginService.getUser() != null) {
			uid = UserLoginService.getUser().id;
		}
		else {
			System.out.println("Problem with user id while loading events");
			return list;
		}
		
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT id, eventName, Start, End, Location, notes, usedServices, createdBy, seid FROM Events WHERE userid="+uid);) {
		         // Extract data from result set
		         while (rs.next()) {
		            // Retrieve by column name

					 //System.out.println(rs.getString("Start"));
		        	Event e=new Event(rs.getInt("id"),rs.getString("eventName"),rs.getTimestamp("Start"),rs.getTimestamp("End"),rs.getString("Location"),rs.getString("notes"),rs.getString("usedServices"), uid, rs.getInt("createdBy"), rs.getInt("seid"));
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
		         ResultSet rs = stmt.executeQuery("SELECT id, eventName, Start, End, Location, notes, usedServices, userid, createdBy, seid FROM Events WHERE id="+id);) {
		         // Extract data from result set
		         if (rs.next()) {
		            // Retrieve by column name
		        	e=new Event(id,rs.getString("eventName"),rs.getTimestamp("Start"),rs.getTimestamp("End"),rs.getString("Location"),rs.getString("notes"),rs.getString("usedServices"), rs.getInt("userid"), rs.getInt("createdBy"), rs.getInt("seid"));
		         }
		      } catch (SQLException ex) {
		         ex.printStackTrace();
		      } 
		return e;
	}
	
	public static String getEventNames() {
		List<String> li=new ArrayList<String>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT DISTINCT eventName FROM Events");) {
		         // Extract data from result set
		        	while (rs.next()) {
		 					// Retrieve by column name
		        		li.add(rs.getString("eventName"));
		 			}
		      } catch (SQLException ex) {
		         ex.printStackTrace();
		      } 
		String s=String.join(",",li);
		return s;
	}
	
	public static String getEventByNames(String o) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT DISTINCT eventName FROM Events WHERE eventName='"+o+"'");) {
		         // Extract data from result set
		        	if (rs.next()) {
		 					// Retrieve by column name
		        		return rs.getString("eventName");
		 			}
		      } catch (SQLException ex) {
		         ex.printStackTrace();
		      } 
		return "";
	}
	
	public static String getEventLocations() {
		List<String> li=new ArrayList<String>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT DISTINCT Location FROM Events");) {
		         // Extract data from result set
		        	while (rs.next()) {
		 					// Retrieve by column name
		        		li.add(rs.getString("Location"));
		 			}
		      } catch (SQLException ex) {
		         ex.printStackTrace();
		      } 
		String s=String.join(",",li);
		return s;
	}

	public static void updateEvent(Event e) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT id, eventName, Start, End, Location, notes, usedServices, userid FROM Events WHERE id="+e.ID);) {
		         // Extract data from result set
			int uid;
			if(UserLoginService.getUser() != null) {
				uid = UserLoginService.getUser().id;
			}
			else {
				System.out.println("Problem with user id while updating event");
				return;
			}
			 if (rs.next()) {
				// Retrieve by column name
				 System.out.println("Preforming Update on Event");
				stmt.executeUpdate("UPDATE Events SET eventName='"+e.getName()+"', Start='"+e.getStartDate()+"', End='"+e.getEndDate()+"', location='"+e.getLocation()+"', notes='"+e.getNote()+"', usedServices='"+e.getUsedServices()+"', userid="+uid+" WHERE id="+e.getID());
			 }
		  } catch (SQLException ex) {
			 ex.printStackTrace();
		  }
	}
	
	public static Event joinEvent(Event e) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();) {
		         // Extract data from result set
			System.out.println("Inserting new Event");
			stmt.executeUpdate("INSERT INTO Events (eventName, Start, End, Location, notes, usedServices, userid, createdBy, seid) VALUES('"+e.getName()+"', '"+e.getStartDate()+"', '"+e.getEndDate()+"', '"+e.getLocation()+"', '"+e.getNote()+"', '"+e.getUsedServices()+"', "+e.userID+", "+e.createdBy+", "+e.seid+")");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return e;
	}
	
	public static Event AddEvent(Event e) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();) {
		         // Extract data from result set
			System.out.println("Inserting new Event");
			stmt.executeUpdate("INSERT INTO Events (eventName, Start, End, Location, notes, usedServices, userid, createdBy) VALUES('"+e.getName()+"', '"+e.getStartDate()+"', '"+e.getEndDate()+"', '"+e.getLocation()+"', '"+e.getNote()+"', '"+e.getUsedServices()+"', "+e.userID+", "+e.userID+")");
			ResultSet rs = stmt.executeQuery("SELECT MAX(ID) FROM Services");
			if(rs.next())
			{
				stmt.executeUpdate("UPDATE Events SET seid="+rs.getInt(1));
				e.seid=rs.getInt(1);
			}
			 stmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return e;
	}
	
	public static void deleteEvent(Event e) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT id FROM Events WHERE id="+e.ID);) {
		         // Extract data from result set
		         if (rs.next()) {
		            // Retrieve by column name
		        	stmt.executeUpdate("DELETE from Events WHERE id="+e.getID());
		         }
		      } catch (SQLException ex) {
		         ex.printStackTrace();
		      }
	}

	public static void deleteEvent(int id) {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM Events WHERE id="+id);) {
			// Extract data from result set
			if (rs.next()) {
				// Retrieve by column name
				stmt.executeUpdate("DELETE from Events WHERE id="+id);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	public static List<Event> getAllEvent() {
		List<Event> li=new ArrayList<Event>();
		List<Integer> l=new ArrayList<Integer>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			) {
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT seid FROM Events");
			// Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				l.add(rs.getInt("seid"));
			}
			for(int i:l)
			{
				rs = stmt.executeQuery("SELECT id, eventName, Start, End, Location, notes, usedServices, userid, createdBy FROM Events where seid="+i);
				while (rs.next()) {
					li.add(new Event(Integer.parseInt(rs.getString("id")), rs.getString("eventName"), rs.getTimestamp("Start"),
					rs.getTimestamp("End"), rs.getString("Location"), rs.getString("notes"),
					rs.getString("usedServices"), rs.getInt("userid"), rs.getInt("createdBy"), i));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return li;
	}
}
