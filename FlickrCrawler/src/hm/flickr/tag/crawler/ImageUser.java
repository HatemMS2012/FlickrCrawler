package hm.flickr.tag.crawler;

public class ImageUser {

	
	private String nsid;
	private String username;
	private String realName;
	private String location;
	private String iconserver;
	private String iconfarm;
	public String getNsid() {
		return nsid;
	}
	public void setNsid(String nsid) {
		this.nsid = nsid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getIconserver() {
		return iconserver;
	}
	public void setIconserver(String iconserver) {
		this.iconserver = iconserver;
	}
	public String getIconfarm() {
		return iconfarm;
	}
	public void setIconfarm(String iconfarm) {
		this.iconfarm = iconfarm;
	}
	
	
	@Override
	public String toString() {
	
		return nsid + " , " + username + " , " + realName + "," + location;
	}
}
