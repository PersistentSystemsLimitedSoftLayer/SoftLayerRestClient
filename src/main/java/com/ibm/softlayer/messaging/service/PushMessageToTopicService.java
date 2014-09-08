package com.ibm.softlayer.messaging.service;

import java.util.List;
import java.util.Map;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import com.ibm.softlayer.client.XAuthTokenSLClient;
import com.ibm.softlayer.util.TokenGenerator;
import com.ibm.softlayer.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

public class PushMessageToTopicService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(PushMessageToTopicService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * 
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public PushMessageToTopicService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}
	
	
	public JSONObject pushMessageToTopic(String topicName,String bodyMessage, int visibilityDelay, Map<String,String> fields) throws Exception {
		logger.info("Executing pushMessageToTopic for username: " + username);
		
		//authenticate the user and retrieve the token
		String token = TokenGenerator.getTokenForMessaging(username, apiKey);
				
				//generate the get queues URL		
				String url = URIGenerator.getSLMessagingAPIURLForTopic();
		
		//append the auth to the URL		
		url += "/" + topicName + "/" + APIConstants.MESSAGES_API;
				
		XAuthTokenSLClient client = new XAuthTokenSLClient(token);
		ClientResponse clientResponse = client.executePOST(url, getJSON(bodyMessage, visibilityDelay, fields));
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed pushMessageToTopic for topic : " + topicName + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 201 || clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("Create Push Message: JSON Response: " + response);
			return json;
		}
		
		throw new Exception("Could not create Push Message: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);				
	}
	
	
	
	private String getJSON(String bodyMessage, int visibilityDelay, Map<String,String> fields) {
		
		JSONObject json = new JSONObject();
		try {
			json.put("body", bodyMessage);
			json.put("fields", new JSONObject(fields));
			json.put("visibility_delay", visibilityDelay);
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}		
		return json.toString();
	}
	
	
	
	
}
