import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event
{
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
    static int ID = 0;
    String name;
    Date sDate;
    Date eDate;
    String loc;
    String note;
    List<Service> usedServices;
    
    Event()
    {
        ID = 0;
        name = "Name";
        sDate = new Date();
        eDate = new Date();
        loc = "";
        note = "";
        usedServices = new ArrayList<Service>();
    }
    //Parse needs to be fixed
    Event(int id, String na, Timestamp timestamp, Timestamp timestamp2, String l, String n, String sl)
    {
        ID = id;
        name = na;
        sDate = new Date();
        //sDate = sdf.parse(sD);
        eDate = new Date();
        //eDate = sdf.parse(eD);
        loc = l;
        note = n;
        String[] r=sl.split(",");
        int[] arr=new int[r.length];
        for (int i = 0; i<r.length; i++) {
            arr[i] = Integer.valueOf(r[i]);
        }
        usedServices=ServiceDAOImp.getServices(arr);
    }

    public int getID()
    {
        return ID;
    }
    public String getName()
    {
        return name;
    }
    public String getStartDate()
    {
        return sdf.format(sDate);
    }
    public String getEndDate()
    {
        return sdf.format(eDate);
    }
    public String getLocation()
    {
        return loc;
    }
    public String getNote()
    {
        return note;
    }

    public void setName(String s)
    {
        name = s;
    }
    public void setID(int i)
    {
        ID = i;
    }
    public void setStartDate(String sD) throws ParseException
    {
        sDate = sdf.parse(sD);
    }
    public void setEndDate(String eD) throws ParseException
    {
        eDate = sdf.parse(eD);
    }
    public void setLocation(String l)
    {
        loc = l;
    }
    public void settNote(String n)
    {
        note = n;
    }

    public Object[] toArray()
    {
        Object[] ev = new Object[]{sDate.toString(), eDate.toString(), loc, name , note," . . . ", " X "};
        return ev;
    }
    public void addService(Service s) {
    	usedServices.add(s);
    }
}