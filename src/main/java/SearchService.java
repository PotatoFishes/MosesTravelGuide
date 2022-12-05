public class SearchService {

    private static String s;
    public static String getData(Object o) {
        s = "Stuff, One, Two, Three, four, five, six, seven, eight, nine, ten, eleven, twelve";
        if (o == "Location") {
            // return all locations
        } else if (o == "Services") {
            // return all services
        } else if (o == "Events") {
            // return all events
        } else {
            // Return events by name
        }

        return s;
    }

}
