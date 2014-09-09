package com.ibm.softlayer.messaging.service;

import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





import com.ibm.softlayer.client.XAuthTokenSLClient;
import com.ibm.softlayer.util.TokenGenerator;
import com.ibm.softlayer.util.URIGenerator;

public class CreateTopicService {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CreateTopicService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new creates the topic service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public CreateTopicService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;		
	}
	
	
	/**
	 * Creates the topic.
	 *
	 * @param topicName the topic name
	 * @param visibilityIntervals the visibility intervals
	 * @param expiration the expiration
	 * @param tags the tags
	 * @return the JSON object
	 * @throws Exception the exception
	 */
	public JSONObject createTopic(String topicName, List<String> tags) throws Exception {
		logger.info("Executing createTopic for username: " + username);
		
		//authenticate the user and retrieve the token
		String token = TokenGenerator.getTokenForMessaging(username, apiKey);
				
				//generate the get queues URL		
				String url = URIGenerator.getSLMessagingAPIURLForTopic();
		
		//append the auth to the URL		
		url += "/" + topicName;
				
		XAuthTokenSLClient client = new XAuthTokenSLClient(token);
		ClientResponse clientResponse = client.executePUT(url, getJSON(tags));
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed createtopic for topicName: " + topicName + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 201 || clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("Create topic: JSON Response: " + response);
			return json;
		}
		
		throw new Exception("Could not create topic: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);				
	}
	
	
	/**
	 * Gets the json.
	 *
	 * @param visibilityIntervals the visibility intervals
	 * @param expiration the expiration
	 * @param tags the tags
	 * @return the json
	 */
	private String getJSON(List<String> tags) {
		
		JSONObject json = new JSONObject();
		try {
			json.put("tags", new JSONArray(tags));
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}		
		return json.toString();
	}
	
	
	

}
