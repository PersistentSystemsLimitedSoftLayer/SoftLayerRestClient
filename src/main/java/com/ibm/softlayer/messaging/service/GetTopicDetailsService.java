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
import com.ibm.softlayer.util.APIConstants;

public class GetTopicDetailsService extends AbstractService {


	/**
	 * Instantiates a new List All Topics service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	
	public GetTopicDetailsService(String username, String apikey) {
		super(username, apikey);
		// TODO Auto-generated constructor stub
	}

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetTopicDetailsService.class);	


	
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
	public JSONObject getTopicDetails(String TopicName) throws Exception {
		logger.info("Executing getTopicDetails for Account: " + "username: " + getUsername());
		
		//authenticate the user and retrieve the token
		String token = getAuthToken();
		
		//generate the get queues URL		
		String url = URIGenerator.getSLMessagingAPIURLForTopic();
		url+="/"+TopicName;
				
		SoftLayerServiceClient client = new SoftLayerServiceClient(token);
		ClientResponse clientResponse = client.executeGET(url, null);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getTopicDetails for Account: " + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 201 || clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("Get Topic Details: JSON Response: " + response);
			return json;
		}
		
		throw new Exception("Could not get topic details: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);				
	}
	


}
