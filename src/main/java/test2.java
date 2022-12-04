
public class test2 {

	public static void main(String[] args) {
		Service e=ServiceDAOImp.getService(2);
		System.out.println(e);
		ServiceDAOImp.updateService(e);

	}

}
