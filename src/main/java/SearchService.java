public class SearchService {

    private static String s;
    public static String getData(Object o) {
        s = "One, Two, Three, four, five, six, seven, eight, nine, ten, eleven, twelve";

        if (o == "Location") {
            s=EventDAOImp.getEventLocations();
        } else if (o == "Services") {
            s=ServiceDAOImp.getServicesNames();
        } else if (o == "Events") {
            s=EventDAOImp.getEventNames();
        } else {
            s=EventDAOImp.getEventByNames((String)o);
        }

        return s;
    }

}
