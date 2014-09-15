package com.ibm.softlayer.images.service;

import java.util.Arrays;
import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

/**
 * The Class ListPublicImagesService.
 */
public class ListPublicImagesService {
	
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ListPublicImagesService.class);	
	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	
	/**
	 * Instantiates a new list public images service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public ListPublicImagesService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}	
	
	/**
	 * List public images.
	 *
	 * @return the JSON array
	 * @throws Exception the exception
	 */
	public JSONArray listPublicImages() throws Exception {
		
		//generate the get image url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.IMAGE_API));
		url.append("/").append("getPublicImages");
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed List Public Images:  Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("List Public Images: JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not retrieve the List Public Images: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	/**
	 * List public images.
	 *
	 * @param filterKey the filter key
	 * @param filterValue the filter value
	 * @param objectMasks the object masks
	 * @return the JSON array
	 * @throws Exception the exception
	 */
	public JSONArray listPublicImages(String filterKey, String filterValue, List<String> objectMasks) throws Exception {
		//generate the get public images url by key
		String url = URIGenerator.getSoftLayerApiUrl(Arrays.asList(APIConstants.IMAGE_API, "getPublicImages"));
		
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url, null, filterKey, filterValue, objectMasks);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed List Public Images:  Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("List Public Images: JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not retrieve the List Public Images: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}

}
