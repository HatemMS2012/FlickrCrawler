package hm.flickr.tag.crawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {

	
	
	private static Connection flcikr2012DBConnection = null ;

	static {
	
		try {	
			
			flcikr2012DBConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/FlickrTags2012",
					"postgres", "admin");
			
//			cleanTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static boolean isImageInDB(String imgId) throws SQLException{

		Statement st = flcikr2012DBConnection.createStatement();
		
		
		//If the image is already in the database ignore it
		
		String selectImage = "SELECT id FROM \"Image\"  WHERE id='"+imgId + "'";
		ResultSet imgRes = st.executeQuery(selectImage);
		
		return imgRes.next();
	}

	public static void insertIntoDB(MyImage img) throws SQLException {
		Statement st = flcikr2012DBConnection.createStatement();
		
		//1 Check if the user is already in the database
		
		String selectUser = "SELECT nsid FROM \"User\"  WHERE nsid='"+img.getUser().getNsid() + "'";
		
		ResultSet usersRs = st.executeQuery(selectUser);
		
		if(!usersRs.next()) {
		
			//If it the user is new create a new record
			
			String userName =  img.getUser().getUsername();
			if(userName !=null){
				userName = userName.replace("'", "''");
			}
			String realName = img.getUser().getRealName();
			if(realName !=null){
				realName = realName.replace("'", "''");
			}
			
			String location = img.getUser().getLocation();
			if(realName !=null){
				location = location.replace("'", "''");
			}
			
			
			
			String insertUser = " INSERT INTO \"User\" (nsid, \"userName\", \"realName\", location) " +
					            " VALUES ('"+ img.getUser().getNsid() 
					                       + "','" + userName 
					                       + "','" + realName 
					                       + "','" + location 
					            		   + "') ";
			
			st.execute(insertUser);
		
		}
		//Insert the location of the photo into the database
		
		String selectLocation = "SELECT longitude, latitued FROM \"Location\" WHERE longitude="+img.getLocation().getLongitude() 
				                                                           + " and latitued="+img.getLocation().getLatitued() ;
		
		ResultSet locRs = st.executeQuery(selectLocation);
		if(!locRs.next()){
		
			String nbh = img.getLocation().getNeighbourhood() ;
			if(nbh!=null){
				nbh = nbh.replace("'", "''");
			}
			
			
			String locality = img.getLocation().getLocality() ;
			if(locality!=null){
				locality = locality.replace("'", "''");
			}
			
			String county = img.getLocation().getCounty() ;
			if(county!=null){
				county = county.replace("'", "''");
			}
			
			String region = img.getLocation().getRegion() ;
			if(region!=null){
				region = region.replace("'", "''");
			}
			
			String country = img.getLocation().getCountry() ;
			if(country!=null){
				country = country.replace("'", "''");
			}
			
			String insertLoc = "INSERT INTO \"Location\" (longitude, latitued, accuracy, \"neighbourhoodPlaceId\", \"neighbourhoodWOEID\"," + 
	                                            "neighbourhood, \"localityPlaceId\", \"localityWOEID\", \"locality\", " + 
	                                            "\"countyPlaceId\", \"countyWOEID\", county, \"regionPlaceId\", \"regionWOEID\"," + 
	                                            "region, \"countryPlaceId\", \"countryWOEID\", country)" + 
	                                            
	                                            " VALUES ("+ img.getLocation().getLongitude() 
	                                            + "," + img.getLocation().getLatitued()
	                                            + "," + img.getLocation().getAccuracy() 
	                                             + ",'" + img.getLocation().getNeighbourhoodPlaceId()
	                                             + "','" + img.getLocation().getNeighbourhoodWOEID() 
	                                             + "','" + nbh
	                                             + "','" + img.getLocation().getLocalityPlaceId() 
	                                             + "','" + img.getLocation().getLocalityWOEID() 
	                                             + "','" + locality
	                                             + "','" + img.getLocation().getCountyPlaceId() 
	                                             + "','" + img.getLocation().getCountyWOEID()
	                                             + "','" + county
	                                             + "','" + img.getLocation().getRegionPlaceId()
	                                             + "','" + img.getLocation().getRegionWOEID()
	                                             + "','" + region
	                                             + "','" + img.getLocation().getCountryPlaceId() 
	                                             + "','" + img.getLocation().getCountryWOEID()
	                                             + "','" + country
	                                            + "') ";

			st.execute(insertLoc);
		}
		
		//Insert the image into the db
		
		String imgDesc = img.getDescription();
		if(imgDesc!=null){
			imgDesc = imgDesc.replace("'", "''");
			imgDesc = imgDesc.replace("\\", "");
			imgDesc = imgDesc.replace("/", "");
			if(imgDesc.length() > 1190){
				imgDesc = imgDesc.substring(0,1190);
			}
		}
		String imgTitle = img.getTitle();
		if(imgTitle!=null){
			imgTitle =	imgTitle.replace("'", "''");
			imgTitle = imgTitle.replace("\\", "");
			imgTitle = imgTitle.replace("/", "");
		}
		
		String insertImage = "INSERT INTO \"Image\"(id, server, farm, favorite, rotation, secret, views, " +
				"\"dateUploaded\", \"canDownloaded\", \"userId\", \"downloadURL\", url, description, title, lon, lat)" +
				"VALUES ('" + img.getId()
				   	     + "','" + img.getServer()
				   	     + "','" + img.getFarm()
				   	     + "'," + img.isFavorite() 
				   	     + "," + img.getRotation()
				   	     + ",'" + img.getSecret()
				   	     + "'," + img.getViews() 
				   	     + ",'" + img.getDateUploaded()
				   	     + "'," + img.isCanDownloaded()
				   	     + ",'" + img.getUser().getNsid()
				   	     + "','" + img.getDowloadURL()
				   	     + "','" + img.getUrl()
				   	     + "','" +  imgDesc
				   	     + "','" + imgTitle
				   	     + "'," + img.getLocation().getLongitude()
						 + "," + img.getLocation().getLatitued()
						 +")"; 
		
		st.execute(insertImage);
	
		//Handle image tags
		for(MyTag tag : img.getTags()){
		
			//Check if the tag exists
			String selectTag  ="SELECT \"normText\" FROM \"Tag\" WHERE \"normText\" = '" + tag.getConcatText() + "'" ;
			
			ResultSet tagRs = st.executeQuery(selectTag);
			
			if(!tagRs.next()){
			
				String text = tag.getText().replace("'", "''");
				text = text.replace("\\", "");
				text = text.replace("/", "");
				
				
				//If the tag does not exists insert it into the tag table
				String insetTag = "INSERT INTO \"Tag\" " +
							                      "VALUES ('" + text
											   	              + "','" +  tag.getConcatText()
											   	              + "'," +  tag.getOrder()
											   	              + "," +  tag.isMachineTag() 
											   	              +")";
				
				
				st.execute(insetTag);
			}

			//Update the image-tag relation table
			
			String insetImaegTag = "INSERT INTO \"imageTag\"(imageid, tag)" +
                                   "   VALUES (" + img.getId() + ",'" + tag.getConcatText() + "')" ;
			
			st.execute(insetImaegTag);
			
			
		}
		st.close();
		

		
	}
	
	public static void cleanTable() throws SQLException{
		
		Statement st = flcikr2012DBConnection.createStatement();
		st.execute("delete from \"imageTag\";  delete from \"Tag\";    delete from \"Image\";  delete from \"Location\" ; delete from \"User\"; ");
		
	}
}
