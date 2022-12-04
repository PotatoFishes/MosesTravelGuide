import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Service
{
    private static final SimpleDateFormat sdf =
            new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
    private static final DecimalFormat df = new DecimalFormat("0.00");
    static int ID = 0;
    double price;
    String name;
    Date sDate;
    Date eDate;
    int bookings = 0;
    int capacity = 0;

    Service() {
        ID = 0;
        name = "Name";
        price = 0f;
        sDate = new Date();
        eDate = new Date();
        capacity = 0;
    }
    Service(int id, String na, double p, Timestamp sD, Timestamp eD, int cap) {
        ID = id;
        name = na;
        price = p;
        sDate = new Date(sD.getTime());
        eDate = new Date(eD.getTime());
        capacity = cap;
    }

    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public float getPrice() {
        return Float.parseFloat(df.format(price));
    }
    public String getStartDate() {
        return sDate.toString();
    }
    public String getEndDate() {
        return eDate.toString();
    }
    public int getCapacity() {
        return capacity;
    }

    public void setID(int id) {
        ID = id;
    }
    public void setName(String n) {
        name = n;
    }
    public void setPrice(float p) {
        price = p;
    }
    public void setStartDate(String sD) throws ParseException {
        sDate = sdf.parse(sD);
    }
    public void setEndDate(String eD) throws ParseException {
        eDate = sdf.parse(eD);
    }
    public void setCapacity(int c) {
        capacity = c;
    }

    public void addBooking()
    {
        bookings++;
    }

    public void subBooking()
    {
        bookings--;
    }

    public Object[] toArray()
    {
        //TODO: make a table in Event Dialog that uses this array
        Object[] ev = new Object[]{ name , bookings , capacity, " . . . ", " X "};
        return ev;
    }

}