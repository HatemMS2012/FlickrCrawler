package hm.flickr.tag.crawler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FlickrTagCrawler {

	
	public String apiKey = "4b1f8453276ecc729863e33beaf96846";
	public String secret = "b0b809b0119b2c1e";
	public String token = "72157626454975218-c6525a27a304ff3e";

	private static float centerLat = 48.86358549323598f;
	private static float centerlon = 2.3651504516601562f;

	
	
	
	
	public FlickrTagCrawler() {

		
	}
	
	
	//n = 15 for Paris
	public static List<GPSCoords> generateCoordinates(float lat, float lon, int n){
		
		List<GPSCoords>  coords = new ArrayList<>();
		
		coords.add(new GPSCoords(lat, lon));
		
		for (int i = 0; i < n; i++) {
			coords.add(new GPSCoords(lat+0.01f, lon));
			coords.add(new GPSCoords(lat-0.01f, lon));
			coords.add(new GPSCoords(lat, lon+0.01f));
			coords.add(new GPSCoords(lat, lon-0.01f));
			coords.add(new GPSCoords(lat+0.01f, lon+0.01f));
			coords.add(new GPSCoords(lat-0.01f, lon-0.01f));
		
		} 
		
		return coords;
		
		
	}

	public static void main(String[] args) throws SQLException {
		
		List<GPSCoords> coords = generateCoordinates(48.86358549f, 2.365150451f, 15);
		for (int k = 1; k < coords.size(); k++) {
			
			GPSCoords coord = coords.get(k);
		
			System.out.println("Deal with corrd index ["+ k + "]" + coord.getLat() + "," + coord.getLon());
				
			for (int i = 1; i <= 60; i++) { //Consider 20 pages
				
				System.out.println("Page: " + i);
				
				FlickrTagCrawler fc = new FlickrTagCrawler();
				
				String flickrRequest = fc.generateFlickrGeoSearchRequest(coord.getLat(),coord.getLon(), 2f, i, 100);
				String flickrResult = fc.handleFlickrRequest(flickrRequest);
				
				System.out.println(flickrResult);
				
				List<String> imageIds = fc.extractImageIds(flickrResult);
		
				System.out.println("Number of found images: " + imageIds.size());
				for (String imageId : imageIds) {
		
					//Ignore already stored images
					if(DBHelper.isImageInDB(imageId)){
						System.err.println("[INFO] Image is in the database");
						continue;
					}
					
					String imgRequest = fc.generateImageSearchRequest(imageId);
					String imgResp = fc.handleFlickrRequest(imgRequest);
					MyImage img = fc.extractImageInfo(imgResp);
			
					if(img.getTags().size()>0){
						
						DBHelper.insertIntoDB(img);
					
					
//						System.out.println(img.getId() + " title: " + img.getTitle());
//						System.out.println(img.getId() + " desc: " + img.getDescription());
//						System.out.println(img.getId() + " user: " + img.getUser());
//						
//						
//						System.out.println(img.getId() + ":" + img.getTags());
//						System.out.println(img.getId() + ":" + img.getLocation());
//						System.out.println(img.getId() + ":" + img.getDowloadURL());
//						
//						System.out.println("---------------------");
					}
					else{
						System.out.println("[NO TAGS:]");
					}
				}
				
			}
			System.out.println("-........................................................-");

		}
	}

	

	/**
	 * Initiate flickr geo search request
	 * 
	 * @param lat
	 * @param lon
	 * @param radius
	 * @param page
	 * @param perPage
	 * @return
	 */
	public String generateFlickrGeoSearchRequest(float lat, float lon,
			float radius, int page, int perPage) {

		String url = null;

		url = "http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key="
				+ apiKey
				+ "&min_taken_date=1%2F1%2F2005"
				+ "&lat="
				+ lat
				+ "&lon="
				+ lon
				+ "&radius="
				+ radius
				+ "&page="
				+ page
				+ "&per_page=" + perPage;

		System.out.println(url);
		return url;
	}

	public String generateImageSearchRequest(String imageId) {

		String request = " http://api.flickr.com/services/rest/"
				+ "?method=flickr.photos.getInfo&" + "api_key=" + apiKey
				+ "&photo_id=" + imageId + "&format=rest";

		return request;

	}

	/**
	 * Take a flickr query as input and return the response in XML format
	 * 
	 * @param req
	 * @return
	 */
	protected String handleFlickrRequest(String req) {
		String response = null;
		URLConnection urlConn;
		try {
			urlConn = new URL(req).openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			String str;

			StringBuffer sb = new StringBuffer();

			while ((str = br.readLine()) != null) {

				sb.append(str);

				sb.append("\n");

			}

			br.close();

			response = sb.toString();

			if (response == null) {
				System.err.println("[Erro] Response is Empty");
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;

	}

	public List<String> extractImageIds(String flickSearchResponse) {

		XMLHelper xmlHelper = new XMLHelper(flickSearchResponse);

		return xmlHelper.extractElementAttribute("photo", "id");
	}

	public MyImage extractImageInfo(String imageFlickXMLResponse) {

		XMLHelper xmlHelper = new XMLHelper(imageFlickXMLResponse);

		MyImage image = new MyImage();

		extractPhotoElementAttrs(xmlHelper, image);
		String canDownload = xmlHelper.extractUniqueElementAttribute("usage","candownload");
		
		//Extract User Information
		
		
		ImageUser user = new ImageUser();
		user.setNsid(xmlHelper.extractUniqueElementAttribute("owner","nsid"));
		user.setRealName(xmlHelper.extractUniqueElementAttribute("owner","realname"));
		user.setLocation(xmlHelper.extractUniqueElementAttribute("owner","location"));
		user.setUsername(xmlHelper.extractUniqueElementAttribute("owner","username"));
		image.setUser(user);
		
		//Extract Title and Description
		image.setDescription(xmlHelper.extractElementValue("description"));
		image.setTitle(xmlHelper.extractElementValue("title"));
		if(canDownload!=null)
			image.setCanDownloaded(canDownload.equals("0") ? false : true);
		image.setLocation(extractLocationElementAttrs(xmlHelper));
		image.setTags(extractTags(xmlHelper));
		image.setUrl(xmlHelper.extractElementValue("url"));

		return image;
	}

	private void extractPhotoElementAttrs(XMLHelper xmlHelper, MyImage image) {
		// Extract Image ID
		String imageId = xmlHelper.extractUniqueElementAttribute("photo", "id");
		image.setId(imageId);
		// Extract Image Secret
		String imageSecret = xmlHelper.extractUniqueElementAttribute("photo",
				"secret");
		image.setSecret(imageSecret);
		// Extract Image
		String imageServer = xmlHelper.extractUniqueElementAttribute("photo",
				"server");
		image.setServer(imageServer);
		String imageFarm = xmlHelper.extractUniqueElementAttribute("photo", "farm");
		image.setFarm(imageFarm);
		String imageDateUploaded = xmlHelper.extractUniqueElementAttribute("photo",
				"dateuploaded");
		image.setDateUploaded(imageDateUploaded);
		String imageIsFavo = xmlHelper.extractUniqueElementAttribute("photo",
				"isfavorite");
		if(imageIsFavo != null)
			image.setFavorite(imageIsFavo.equals("0") ? false : true);
		String imageLicense = xmlHelper.extractUniqueElementAttribute("photo",
				"license");
		image.setLicense(imageLicense);
		String imageSafetlyLevel = xmlHelper.extractUniqueElementAttribute("photo",
				"safety_level");
		image.setSafetyLevel(imageSafetlyLevel);
		String imageRotation = xmlHelper.extractUniqueElementAttribute("photo",
				"rotation");
		image.setRotation(imageRotation);
		String imageOriginalSecret = xmlHelper.extractUniqueElementAttribute("photo",
				"originalsecret");
		image.setOriginalSecret(imageOriginalSecret);
		String imageOriginalFormat = xmlHelper.extractUniqueElementAttribute("photo",
				"originalformat");
		image.setOriginalFormat(imageOriginalFormat);
		String imageViews = xmlHelper.extractUniqueElementAttribute("photo", "views");
		image.setViews(Integer.valueOf(imageViews));
		String imageMedia = xmlHelper.extractUniqueElementAttribute("photo", "media");
		image.setMedia(imageMedia);
	}

	private MyLocation extractLocationElementAttrs(XMLHelper xmlHelper) {
		MyLocation myLocation = new MyLocation();
		// Extract Image ID
		myLocation.setLatitued(Float.valueOf(xmlHelper.extractUniqueElementAttribute(
				"location", "latitude")));
		myLocation.setLongitude(Float.valueOf(xmlHelper
				.extractUniqueElementAttribute("location", "longitude")));
		myLocation.setAccuracy(Integer.valueOf(xmlHelper
				.extractUniqueElementAttribute("location", "accuracy")));

		myLocation.setCountryPlaceId(xmlHelper.extractUniqueElementAttribute(
				"country", "place_id"));
		myLocation.setCountryWOEID(xmlHelper.extractUniqueElementAttribute("country",
				"woeid"));
		myLocation.setCountry(xmlHelper.extractElementValue("country"));

		myLocation.setCountyPlaceId(xmlHelper.extractUniqueElementAttribute("county",
				"place_id"));
		myLocation.setCountyWOEID(xmlHelper.extractUniqueElementAttribute("county",
				"woeid"));
		myLocation.setCounty(xmlHelper.extractElementValue("county"));

		myLocation.setRegionPlaceId(xmlHelper.extractUniqueElementAttribute("region",
				"place_id"));
		myLocation.setRegionWOEID(xmlHelper.extractUniqueElementAttribute("region",
				"woeid"));
		myLocation.setRegion(xmlHelper.extractElementValue("region"));

		myLocation.setLocalityPlaceId(xmlHelper.extractUniqueElementAttribute(
				"locality", "place_id"));
		myLocation.setLocalityWOEID(xmlHelper.extractUniqueElementAttribute(
				"locality", "woeid"));
		myLocation.setLocality(xmlHelper.extractElementValue("locality"));

		myLocation.setNeighbourhoodPlaceId(xmlHelper.extractUniqueElementAttribute(
				"neighbourhood", "place_id"));
		myLocation.setNeighbourhoodWOEID(xmlHelper.extractUniqueElementAttribute(
				"neighbourhood", "woeid"));
		myLocation.setNeighbourhood(xmlHelper
				.extractElementValue("neighbourhood"));

		return myLocation;
	}

	private List<MyTag> extractTags(XMLHelper xmlHelper) {

		List<MyTag> tags = new ArrayList<>();

		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr;
		try {
			expr = xpath.compile("//tag");
			Object result = expr.evaluate(xmlHelper.getDoc(),
					XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			for (int i = 0; i < nodes.getLength(); i++) {

				MyTag myTag = new MyTag();

				String text = nodes.item(i).getAttributes().getNamedItem("raw")
						.getNodeValue();

				String concatText = nodes.item(i).getChildNodes().item(0)
						.getNodeValue();

				String tech = nodes.item(i).getAttributes()
						.getNamedItem("machine_tag").getNodeValue();

				myTag.setText(text);
				myTag.setConcatText(concatText);
				if(tech!=null)
					myTag.setMachineTag(tech.equals("0") ? false : true);
				tags.add(myTag);
			}

		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tags;

	}

}
