import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Event
{
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY hh:mm a");
    static int ID = 0;
    String name;
    Date sDate;
    Date eDate;
    String loc;
    String note;
    List<Service> usedServices = new ArrayList<Service>();
    int userID;
    
    Event()
    {
        ID = 0;
        name = "Name";
        sDate = new Date();
        eDate = new Date();
        loc = "";
        note = "";
    }

    Event(String na, Timestamp timestamp, Timestamp timestamp2, String l, String n, String sl, int uid)
    {
        name = na;
        sDate = new Date(timestamp.getTime());
        eDate = new Date(timestamp2.getTime());
        //eDate = sdf.parse(eD);
        loc = l;
        note = n;
        String[] r = null;
        if(!sl.equals(""))
        {
            r=sl.split(",");
            int[] arr=new int[r.length];
            for (int i = 0; i<r.length; i++) {
                arr[i] = Integer.valueOf(r[i]);
            }
            usedServices=ServiceDAOImp.getServices(arr);
        }
        userID=uid;
    }
    //Parse needs to be fixed
    Event(int id, String na, Timestamp timestamp, Timestamp timestamp2, String l, String n, String sl, int uid)
    {
        ID = id;
        name = na;
        sDate = new Date(timestamp.getTime());
        eDate = new Date(timestamp2.getTime());
        //eDate = sdf.parse(eD);
        loc = l;
        note = n;
        String[] r = null;
        if(!sl.equals(""))
        {
        	r=sl.split(",");
        	Vector<Integer> arr = new Vector<Integer>();
        	for (int i = 0; i<r.length; i++) {
        		try {
        			arr.add( Integer.valueOf(r[i]));
        		}
        		catch(NumberFormatException nfe) {
        		}
        	}
        	int[] data = arr.stream().mapToInt(a->a).toArray();
        	
        	usedServices=ServiceDAOImp.getServices(data);
        }
        userID=uid;
    }

    @Override
	public int hashCode() {
		return Objects.hash(eDate, loc, name, note, sDate, sdf, usedServices, userID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(eDate, other.eDate) && Objects.equals(loc, other.loc) && Objects.equals(name, other.name)
				&& Objects.equals(note, other.note) && Objects.equals(sDate, other.sDate)
				&& Objects.equals(sdf, other.sdf) && Objects.equals(usedServices, other.usedServices)
				&& userID == other.userID;
	}

	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
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

    public String getUsedServices() {
    	List<String> l=new ArrayList<String>();
    	String us=null;
    	if(!usedServices.isEmpty())
    	{
    		for(Service s:usedServices)
    			l.add(""+s.getID());
    		us=String.join(",", l);
    	}
		return us;
	}
    public List<Service> getUsedServices(List<Service> serv){
    	return usedServices;
    }
	@Override
	public String toString() {
		return "Event [id=" + ID + ", name=" + name + ", sDate=" + sDate + ", eDate=" + eDate + ", loc=" + loc
				+ ", note=" + note + ", usedServices=" + usedServices + ", userID=" + userID + "]";
	}
	public void setUsedServices(List<Service> usedServices) {
		this.usedServices = usedServices;
	}
	public Object[] toArray()
    {
        Object[] ev = new Object[]{"" + ID, sdf.format(sDate), sdf.format(eDate), loc, name , note," . . . ", " X "};
        return ev;
    }
    public void addService(Service s) {
    	usedServices.add(s);
    }
}