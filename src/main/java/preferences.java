
public class preferences {
	int uid;
	boolean noti,priv;
	String gender;
	
	public preferences(int uid, boolean noti, boolean priv, String gender) {
		super();
		this.uid = uid;
		this.noti = noti;
		this.priv = priv;
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "preferences [noti=" + noti + ", priv=" + priv + ", gender=" + gender + "]";
	}
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public boolean isNoti() {
		return noti;
	}

	public void setNoti(boolean noti) {
		this.noti = noti;
	}

	public boolean isPriv() {
		return priv;
	}

	public void setPriv(boolean priv) {
		this.priv = priv;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
