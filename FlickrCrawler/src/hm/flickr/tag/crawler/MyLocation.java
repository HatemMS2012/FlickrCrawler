package hm.flickr.tag.crawler;

public class MyLocation {

	private float longitude;
	private float latitued;
	private int accuracy;

	private String neighbourhoodPlaceId ;
	private String neighbourhoodWOEID ;
	private String neighbourhood;
	private String localityPlaceId;
	private String localityWOEID;
	private String locality;
	private String countyPlaceId;
	private String countyWOEID;
	private String county;
	private String regionPlaceId;
	private String regionWOEID;
	private String region;
	private String countryPlaceId;
	private String countryWOEID;
	private String country;

	
	
	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitued() {
		return latitued;
	}

	public void setLatitued(float latitued) {
		this.latitued = latitued;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public String getNeighbourhoodPlaceId() {
		return neighbourhoodPlaceId;
	}

	public void setNeighbourhoodPlaceId(String neighbourhoodPlaceId) {
		this.neighbourhoodPlaceId = neighbourhoodPlaceId;
	}

	public String getNeighbourhoodWOEID() {
		return neighbourhoodWOEID;
	}

	public void setNeighbourhoodWOEID(String neighbourhoodWOEID) {
		this.neighbourhoodWOEID = neighbourhoodWOEID;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public String getLocalityPlaceId() {
		return localityPlaceId;
	}

	public void setLocalityPlaceId(String localityPlaceId) {
		this.localityPlaceId = localityPlaceId;
	}

	public String getLocalityWOEID() {
		return localityWOEID;
	}

	public void setLocalityWOEID(String localityWOEID) {
		this.localityWOEID = localityWOEID;
	}

	public String getCountyPlaceId() {
		return countyPlaceId;
	}

	public void setCountyPlaceId(String countyPlaceId) {
		this.countyPlaceId = countyPlaceId;
	}

	public String getCountyWOEID() {
		return countyWOEID;
	}

	public void setCountyWOEID(String countyWOEID) {
		this.countyWOEID = countyWOEID;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getRegionPlaceId() {
		return regionPlaceId;
	}

	public void setRegionPlaceId(String regionPlaceId) {
		this.regionPlaceId = regionPlaceId;
	}

	public String getRegionWOEID() {
		return regionWOEID;
	}

	public void setRegionWOEID(String regionWOEID) {
		this.regionWOEID = regionWOEID;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountryPlaceId() {
		return countryPlaceId;
	}

	public void setCountryPlaceId(String countryPlaceId) {
		this.countryPlaceId = countryPlaceId;
	}

	public String getCountryWOEID() {
		return countryWOEID;
	}

	public void setCountryWOEID(String countryWOEID) {
		this.countryWOEID = countryWOEID;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	@Override
	public String toString() {
		
		return "local:"+locality + ",county:" + country + ",nbh:" + neighbourhood + ",country:" + country;
	}

}
