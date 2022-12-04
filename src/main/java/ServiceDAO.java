
import java.util.List;

public interface ServiceDAO {

	public List<Service> getServices(int id);
	public Service getService(int id);
	public void updateService(Service e);
	public void deleteService(Service e);
}