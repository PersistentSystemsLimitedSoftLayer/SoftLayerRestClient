package com.ibm.softlayer.images.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;



public class EditImageService {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(EditImageService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	public EditImageService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}

	
	
	public Boolean editImageObject(String imageId, JSONObject bodyJson) throws Exception {
		
		//authenticate the user and retrieve the token
		
		
		
		logger.info("Executing edit Image for image id: " + imageId);
		if(imageId == null || imageId.trim().length() == 0){
			throw new Exception("Image Id is mandatory to edit the Image");
		}
		
		//generate the edit image url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.IMAGE_API));
		url.append("/").append(imageId).append("/editObject");
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executePOST(url.toString(),getJSON(bodyJson));
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed edit Image: " + imageId + ", Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			Boolean isedited = new Boolean(response);
			logger.debug("edit Image: JSON Response: " + response);
			return isedited;		
		}
		
		throw new Exception("Could not edit the Image: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	
	private String getJSON(JSONObject bodyJson) {
		
		JSONArray jarr = new JSONArray();
		jarr.add(bodyJson);
		
		Map<String,Object> bodyMap = new HashMap<String, Object>();
		bodyMap.put("parameters", jarr);
		
		JSONObject json = new JSONObject(bodyMap);
		
		System.out.println("obj json ===== "+json);
				
		return json.toString();
	}
	
	

}
