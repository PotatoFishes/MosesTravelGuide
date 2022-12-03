import java.util.List;

public interface EventDAO {

	public List<Event> getEvents(int uid);
	public Event getEvent(int id);
	public void updateEvent(Event e);
	public void deleteEvent(Event e);
}
