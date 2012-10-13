package hm.flickr.tag.crawler;

public class GPSCoords {

	float lat ;
	float lon;
	
	
	public GPSCoords(float lat, float lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	
	
	
}
