package com.ibm.softlayer.images.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;

import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class GetImageDetailsService.
 */
public class GetImageDetailsService  {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetImageDetailsService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new gets the image details service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public GetImageDetailsService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}	
	
	/**
	 * Gets the image object.
	 *
	 * @param imageId the image id
	 * @return the image object
	 * @throws Exception the exception
	 */
	public JSONObject getImageObject(String imageId) throws Exception {
		
		logger.info("Executing Get Image: " + imageId);
		if(imageId == null || imageId.trim().length() == 0){
			throw new Exception("Image Id is mandatory to retrieve the Image Details");
		}
		
		//generate the get image url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.IMAGE_API));
		url.append("/").append(imageId);
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed Get Image: " + imageId + ", Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("Get Image: JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not retrieve the Image: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}

}
