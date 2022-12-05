import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceServ {
	private ServiceDAOImp servDao = new ServiceDAOImp();

	public List<Service> getServiceList(String commaSeparatedIDs)
	{
		List<Service> usedServices = new ArrayList<>();
		String csid = commaSeparatedIDs;
		String[] r = null;
		if(!csid.equals(""))
		{
			r=csid.split(",");
			int[] arr=new int[r.length];
			for (int i = 0; i<r.length; i++) {
				arr[i] = Integer.valueOf(r[i]);
			}
			usedServices=ServiceDAOImp.getServices(arr);
		}
		return usedServices;
	}

	public static Object[][] getServicesForTable(int eid) {
		java.util.List<Service> services = new ArrayList<>();
		EventDAOImp.getEvent(eid).getUsedServices(services);
		Object[][] data = new Object[services.size()][];
		for(int i = 0; i < data.length; ++i) {
			data[i] = services.get(i).toArray();
		}
		return data;
	}

	public void book(Service s) {
		s.addBooking();
	}
	public void unbook(Service s) {
		s.subBooking();
	}
	public void addService(Service s) {
		
	}
	public void removeService(Service s) {
		
	}
}
