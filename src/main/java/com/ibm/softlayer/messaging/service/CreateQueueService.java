package com.ibm.softlayer.messaging.service;

import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;

/**
 * The Class CreateQueueService.
 */
public class CreateQueueService extends AbstractService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CreateQueueService.class);	
	
	/**
	 * Instantiates a new creates the queue service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public CreateQueueService(String username, String apikey) {
		super(username, apikey);		
	}
	
	/**
	 * Creates the queue.
	 *
	 * @param queueName the queue name
	 * @param visibilityIntervals the visibility intervals
	 * @param expiration the expiration
	 * @param tags the tags
	 * @return the JSON object
	 * @throws Exception the exception
	 */
	public JSONObject createQueue(String queueName, int visibilityIntervals, int expiration, List<String> tags) throws Exception {
		logger.info("Executing createQueue for username: " + getUsername());
		
		//authenticate the user and retrieve the token
		String token = getAuthToken();
		
		//generate the get queues URL		
		String url = URIGenerator.getSLMessagingAPIURL();
		
		//append the auth to the URL		
		url += "/" + queueName;
				
		SoftLayerServiceClient client = new SoftLayerServiceClient(token);
		ClientResponse clientResponse = client.executePUT(url, getJSON(visibilityIntervals, expiration, tags));
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed createQueue for QueueName: " + queueName + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 201 || clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("Create Queue: JSON Response: " + response);
			return json;
		}
		
		throw new Exception("Could not create Queue: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);				
	}
	
	
	/**
	 * Gets the json.
	 *
	 * @param visibilityIntervals the visibility intervals
	 * @param expiration the expiration
	 * @param tags the tags
	 * @return the json
	 */
	private String getJSON(int visibilityIntervals, int expiration, List<String> tags) {
		
		JSONObject json = new JSONObject();
		try {
			json.put("visibility_interval", visibilityIntervals);
			json.put("expiration", expiration);
			json.put("tags", new JSONArray(tags));
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}		
		return json.toString();
	}
}
