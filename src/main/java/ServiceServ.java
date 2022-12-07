import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceServ {

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

	public static Object[][] getServicesForTable(Event e) {
		java.util.List<Service> services = new ArrayList<>();
		services = e.getUsedServices(services);
		Object[][] data = new Object[services.size()][];
		for(int i = 0; i < data.length; ++i) {
			data[i] = services.get(i).toArray();
		}
		return data;
	}

	public static Object[][] getAllServicesForTable() {
		java.util.List<Service> events =  ServiceDAOImp.getAllServices();
		Object[][] data = new Object[events.size()][];
		for(int i = 0; i < data.length; ++i) {
			data[i] = events.get(i).toArray();
		}
		return data;
	}

	public void book(Service s) {
		s.addBooking();
	}
	public void unbook(Service s) {
		s.subBooking();
	}
	public static Service addService(Service s) {
		Service se=ServiceDAOImp.addNewService(s);
		return se;
	}
	public static void createService(Service s) {
		ServiceDAOImp.updateService(s);
	}
	public static void removeService(Service s) {
		ServiceDAOImp.deleteService(s);
	}
}
