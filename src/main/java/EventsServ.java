import java.text.ParseException;
import java.util.List;

public class EventsServ {
	public List<Booking> getBookings(){
		
		return null;
	}
	
	public Event createEvent() {
		return new Event();
	}
	
	public Event createEvent(int id, String na, int t, String sD, String eD, String l, String n) throws ParseException {
		return new Event(id, na, t, sD, eD, l, n);
	}
	
	public void setEvent(Event e) {
		
	}
}
