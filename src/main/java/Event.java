import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class Event implements Comparator<Event>
{
    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss a");

    String note = "";
    String name = "";

    public void setStart(String d) throws ParseException
    {
        start.setTime(sdf.parse(d));
    }
    public Date getStart()
    {
        return start.getTime();
    }

    public void setEnd(String d)throws ParseException
    {
        end.setTime(sdf.parse(d));
    }
    public Date getEnd()
    {
        return end.getTime();
    }

    public void setNote(String n)
    {
        note = n;
    }

    public int compareTo(Event p2)
    {
        return this.name.compareTo(p2.name);
    }

    @Override
    public int compare(Event o1, Event o2) {
        return 0;
    }
}