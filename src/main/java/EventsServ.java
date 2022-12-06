import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventsServ {
	public static List<Booking> getBookings(){
		Set<Booking> out = new HashSet<Booking>();
		java.util.List<Event> events = EventDAOImp.getEvents();
		for(Event e: events) {
			List<Service> serv = e.getUsedServices(null);
			for(Service s: serv) {
				out.add(new Booking(e, s));
			}
		}
		return new ArrayList<Booking>(out);
	}
	
	public static void createEvent(Event e)
	{
		EventDAOImp.updateEvent(e);
	}

	public static boolean checkTimesValid(Event e) throws ParseException {
		boolean valid = true;
		Timestamp timestamp = CreateEvent.convertStringToTimestamp(e.getStartDate());
		Timestamp timestamp2 = CreateEvent.convertStringToTimestamp(e.getEndDate());

		valid = timestamp.before(timestamp2);
		
		for(Event ev: EventDAOImp.getEvents())
		{
			if(!valid)
			{
				break;
			}
			valid = CreateEvent.convertStringToTimestamp(ev.getStartDate()).before(timestamp2)
					|| CreateEvent.convertStringToTimestamp(ev.getEndDate()).after(timestamp);
			
		}
		return valid;
	}
	
	public static Event createEvent(int id, String na, int t, String sD, String eD, String l, String n) throws ParseException {
		return null;//new Event(id, na, t, sD, eD, l, n);
	}

	public static void removeEvent(int id)
	{
		EventDAOImp.deleteEvent(id);
	}

	public static List<Event> getEventsForPlanner()
	{
		return EventDAOImp.getEvents();
	}
	public static Object[][] getAllEventsForTable() {
		java.util.List<Event> events = EventDAOImp.getAllEvent();
		Object[][] data = new Object[events.size()][];
		for(int i = 0; i < data.length; ++i) {
			data[i] = events.get(i).toArray();
		}
		return data;
	}
	
	public static Object[][] getEventsForTable() {
		java.util.List<Event> events = EventDAOImp.getEvents();
        Object[][] data = new Object[events.size()][];
        for(int i = 0; i < data.length; ++i) {
        	data[i] = events.get(i).toArray();
        }
        return data;
	}
}
