package com.ibm.softlayer.messaging.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;

/**
 * The Class GetQueuesService.
 */
public class GetQueuesService extends AbstractService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetQueuesService.class);	

	public GetQueuesService(String username, String apiKey) {
		super(username, apiKey);
	}		
	
	/**
	 * Gets the queues.
	 *
	 * @param accountId the account id
	 * @param tags the tags
	 * @param username the username
	 * @param apikey the apikey
	 * @return the queues
	 * @throws Exception the exception
	 */
	public JSONArray getQueues(String tags) throws Exception {
		return getAllQueues(tags)	;
	}
	
	/**
	 * Gets the all queues.
	 *
	 * @param accountId the account id
	 * @param tags the tags
	 * @param username the username
	 * @param apikey the apikey
	 * @return the all queues
	 * @throws Exception the exception
	 */
	private JSONArray getAllQueues(String tags) throws Exception {
		logger.info("Executing getQueues for username: " + getUsername());
		
		//authenticate the user and retrieve the token
		String token = getAuthToken();
		
		//generate the get queues URL		
		String url = URIGenerator.getSLMessagingAPIURL();
		
		//check if tags exists, add it as request params
		Map<String, String> requestParams = new HashMap<String, String>();
		if(tags != null) {
			requestParams.put("tags", tags);
		}
		
		SoftLayerServiceClient client = new SoftLayerServiceClient(token);
		ClientResponse clientResponse = client.executeGET(url, requestParams);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getQueues for username: " + getUsername() + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("GetQueue by Name: JSON Response: " + response);
			return (JSONArray) json.get("items");			
		}
		
		throw new Exception("Could not retrieve the token: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	/**
	 * Gets the queues.
	 *
	 * @param accountId the account id
	 * @param username the username
	 * @param apikey the apikey
	 * @return the queues
	 * @throws Exception the exception
	 */
	public JSONArray getQueues() throws Exception {
		return getAllQueues(null);
	}
}
