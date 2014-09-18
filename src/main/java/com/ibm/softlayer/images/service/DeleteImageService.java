package com.ibm.softlayer.images.service;

import java.util.Map;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;



public class DeleteImageService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DeleteImageService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	public DeleteImageService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}

	
	/**
	 * Delete the image.
	 *
	 * @param imageId the image id
	 * @return the JSONObject object
	 * @throws Exception the exception
	 */
	public JSONObject deleteImageObject(String imageId) throws Exception {
		
		//authenticate the user and retrieve the token

		
		logger.info("Executing delete Image for image id: " + imageId);
		if(imageId == null || imageId.trim().length() == 0){
			throw new Exception("Image Id is mandatory to delete the Image");
		}
		
		//generate the delete image url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.IMAGE_API));
		url.append("/").append(imageId);
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeDELETE(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed delete Image: " + imageId + ", Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200 || clientResponse.getStatusCode() == 202){
			JSONObject json = new JSONObject(response);
			logger.debug("delete Image: JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not delete the Image: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	
}
