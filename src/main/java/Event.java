import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event
{
    SimpleDateFormat sdf =
            new SimpleDateFormat("MM/dd/YYYY hh:mm:ss a");
    static int ID = 0;
    String name;
    int type;
    Date sDate;
    Date eDate;
    String loc;
    String note;
    Event()
    {
        ID = 0;
        name = "Name";
        type = 0;
        sDate = new Date();
        eDate = new Date();
        loc = "";
        note = "";
    }
    //Parse needs to be fixed
    Event(int id, String na, int t, String sD, String eD, String l, String n) throws ParseException
    {
        ID = id;
        name = na;
        type = t;
        sDate = new Date();
        //sDate = sdf.parse(sD);
        eDate = new Date();
        //eDate = sdf.parse(eD);
        loc = l;
        note = n;
    }

    public int getID()
    {
        return ID;
    }
    public String getName()
    {
        return name;
    }
    public int getType()
    {
        return type;
    }
    public String getStartDate()
    {
        return sDate.toString();
    }
    public String getEndDate()
    {
        return eDate.toString();
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
    public void setType(int t)
    {
        type = t;
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
        Object[] ev = new Object[]{sDate.toString(), eDate.toString(), name , note,"Edit", "Remove"};
        return ev;
    }

}