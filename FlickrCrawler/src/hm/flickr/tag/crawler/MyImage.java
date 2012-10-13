package hm.flickr.tag.crawler;

import java.util.List;

public class MyImage {

	
	private String id ;
	private String server;
	private String farm;
	private String secret;
	private boolean isFavorite;
	private String license; 
	private String safetyLevel;
	private String rotation;
	private String originalSecret; 
	private String originalFormat;
	private int views;
	private String media;
	private String dateUploaded;
	private String takenGranularity;
	private String lastUpdate;
	private boolean canDownloaded ;
	private List<String> comments ;
	private List<MyTag> tags ;
	private MyLocation location;
	private ImageUser user ;
	private String url ;
	private String dowloadURL;  //http://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg 
	private String description;
	private String title;


	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setDowloadURL(String dowloadURL) {
		this.dowloadURL = dowloadURL;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getFarm() {
		return farm;
	}
	public void setFarm(String farm) {
		this.farm = farm;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public boolean isFavorite() {
		return isFavorite;
	}
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getSafetyLevel() {
		return safetyLevel;
	}
	public void setSafetyLevel(String safetyLevel) {
		this.safetyLevel = safetyLevel;
	}
	public String getRotation() {
		return rotation;
	}
	public void setRotation(String rotation) {
		this.rotation = rotation;
	}
	public String getOriginalSecret() {
		return originalSecret;
	}
	public void setOriginalSecret(String originalSecret) {
		this.originalSecret = originalSecret;
	}
	public String getOriginalFormat() {
		return originalFormat;
	}
	public void setOriginalFormat(String originalFormat) {
		this.originalFormat = originalFormat;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public String getDateUploaded() {
		return dateUploaded;
	}
	public void setDateUploaded(String dateUploaded) {
		this.dateUploaded = dateUploaded;
	}
	public String getTakenGranularity() {
		return takenGranularity;
	}
	public void setTakenGranularity(String takenGranularity) {
		this.takenGranularity = takenGranularity;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public boolean isCanDownloaded() {
		return canDownloaded;
	}
	public void setCanDownloaded(boolean canDownloaded) {
		this.canDownloaded = canDownloaded;
	}
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	public List<MyTag> getTags() {
		return tags;
	}
	public void setTags(List<MyTag> tags) {
		this.tags = tags;
	}
	public MyLocation getLocation() {
		return location;
	}
	public void setLocation(MyLocation location) {
		this.location = location;
	}
	public ImageUser getUser() {
		return user;
	}
	public void setUser(ImageUser user) {
		this.user = user;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDowloadURL() {

		dowloadURL = "http://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+".jpg";
		return dowloadURL;
	}

	
}
