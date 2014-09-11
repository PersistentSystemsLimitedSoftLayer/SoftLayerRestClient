package com.ibm.softlayer.images.service;

import java.util.Map;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetIdFrmNamePublicPrivateService {


	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetIdFrmNamePublicPrivateService.class);	
	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	
	public GetIdFrmNamePublicPrivateService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}
	
	

	
	
	/*public String getIdFromNamePublic(Map<String,Object> filterMap) throws Exception {
		
		//authenticate the user and retrieve the token
		
		
		//generate the get image url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.IMAGE_API));
		url.append("/").append("getPublicImages?objectFilter=").append(getJSON(filterMap));
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getIdFromNamePublic Image:  Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("getIdFromNamePublic : " + response);
			return json.getString("id") ;		
		}
		
		throw new Exception("Could not retrieve the getIdFromNamePublic Images: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	
	private String getJSON(Map<String,Object> filterMap) {
			
			System.out.println("obj json ===== "+new JSONObject(filterMap));
			
			JSONObject json = new JSONObject(filterMap);
					
			return json.toString();
	}*/
	
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}


	public String getIdFromNamePublic(String ImageName) throws Exception {
		String id = null;
		ListPublicImagesService service = new ListPublicImagesService(getUsername(), getApiKey());
		JSONArray imageJson =  service.listPublicImages();
		for(int i=0; i<imageJson.length();i++){
			JSONObject jobj = (JSONObject) imageJson.get(i);
			if(ImageName.equalsIgnoreCase(jobj.getString("name"))){
				id = jobj.getString("id");
			}
		}
		return id;
		
	}
	
	
	public String getIdFromNamePrivate(String ImageName) throws Exception {
		String id = null;
		ListPrivateImagesService service = new ListPrivateImagesService(getUsername(), getApiKey());
		JSONArray imageJson =  service.listPrivateImages();
		for(int i=0; i<imageJson.length();i++){
			JSONObject jobj = (JSONObject) imageJson.get(i);
			if(ImageName.equalsIgnoreCase(jobj.getString("name"))){
				id = jobj.getString("id");
			}
		}
		return id;
		
	}
	
	
	
	
}
