import java.text.ParseException;
import java.util.List;

public class EventsServ {
	public static List<Booking> getBookings(){
		
		return null;
	}
	
	public static Event createEvent() {
		return new Event();
	}
	
	public static Event createEvent(int id, String na, int t, String sD, String eD, String l, String n) throws ParseException {
		return null;//new Event(id, na, t, sD, eD, l, n);
	}
	
	public static void setEvent(Event e) {
		
	}
	
	public static Object[][] getEventsForTable() {
		java.util.List<Event> events = EventDAOImp.getEvents();
        Object[][] data = new Object[events.size()][];
        for(int i = 0; i < data.length; ++i) {
        	data[i] = new Object[]{
        			events.get(i).sDate,
        			events.get(i).eDate,
        			events.get(i).loc,
        			events.get(i).name,
        			events.get(i).note,
        			" . . . ",
        			" X "};
        }
        return data;
	}
}
