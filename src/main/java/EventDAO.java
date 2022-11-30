import java.util.List;

public interface EventDAO {

	public List<Event> getEvents();
	public Event getEvent(int uid);
	public void updateEvent();
	public void deleteEvent();
}
