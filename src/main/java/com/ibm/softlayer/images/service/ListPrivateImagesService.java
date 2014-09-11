package com.ibm.softlayer.images.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;



public class ListPrivateImagesService {
	
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ListPrivateImagesService.class);	
	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	
	public ListPrivateImagesService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}

	
	
	public JSONArray listPrivateImages() throws Exception {
		
		//authenticate the user and retrieve the token
		
		
		//generate the get image url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.ACCOUNT_ROOT_API));
		url.append("/").append("getPrivateBlockDeviceTemplateGroups");
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed List Private Images:  Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("List Private Images: JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not retrieve the List Private Images: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}

}
