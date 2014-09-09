package com.ibm.softlayer.vs.service;

import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class GetAllVirtalServers.
 */
public class GetAllVirtalServers {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetAllVirtalServers.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;	
	
	/**
	 * Instantiates a new gets the all virtal servers.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public GetAllVirtalServers(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
	}
	
	/**
	 * Find all.
	 *
	 * @return the JSON array
	 * @throws Exception the exception
	 */
	public JSONArray findAll() throws Exception {
		
		logger.info("Executing findAll: Virtual Servers for username: " + username);
		
		//generate the get instance url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.ACCOUNT_ROOT_API));
		url.append("/").append("VirtualGuests");
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString(), null);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed findAll: Virtual Servers for username: " + username + ", Response Status Code: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("Executed findAll: Virtual Servers: JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not execute findAll: Virtual Servers: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);		
	}
	
	/**
	 * Find all.
	 *
	 * @param objectMasks the object masks
	 * @return the JSON array
	 * @throws Exception the exception
	 */
	public JSONArray findAll(List<String> objectMasks) throws Exception {
		
		logger.info("Executing findAll: Virtual Servers for username: " + username);
		
		//generate the get instance url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.ACCOUNT_ROOT_API));
		url.append("/").append("VirtualGuests");
		
		//setting the object masks
		processObjectMasks(url, objectMasks);
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString(), null);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed findAll: Virtual Servers for username: " + username + ", Response Status Code: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("Executed findAll: Virtual Servers: JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not execute findAll: Virtual Servers: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);		
	}
	
	/**
	 * Process object masks.
	 *
	 * @param url the url
	 * @param objectMasks the object masks
	 */
	private void processObjectMasks(StringBuffer url, List<String> objectMasks) {
		//setting the object masks
		if(objectMasks != null && objectMasks.size() > 0){
			StringBuffer mask = new StringBuffer();
			for(String maskVal : objectMasks) {
				if(mask.toString().trim().length() > 0){
					mask.append(";");
				}
				mask.append(maskVal);
			}
			
			//append the mask to the URL
			if(mask.toString().trim().length() > 0){
				url.append("?").append("objectMask=").append(mask.toString());
			}
		}
	}
}
