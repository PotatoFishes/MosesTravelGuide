import java.util.Objects;

public class Booking {
	private Event event;
	private Service service;
	public Booking() {
		event = null;
		service = null;
	}
	public Booking(Event e, Service s) {
		event = e;
		service = s;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	@Override
	public int hashCode() {
		return Objects.hash(event, service);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		return Objects.equals(event, other.event) && Objects.equals(service, other.service);
	}
}
