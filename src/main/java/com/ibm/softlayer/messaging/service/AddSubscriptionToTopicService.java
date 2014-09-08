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

public class AddSubscriptionToTopicService {
	
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AddSubscriptionToTopicService.class);	
	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * add subscription topic service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public AddSubscriptionToTopicService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;		
	}
	
	
	/**
	 * add subscription the topic, queue as a endpoint.
	 *
	 * @param topicName the topic name
	 * @param 
	 * @param expiration the expiration
	 * @param tags the tags
	 * @return the JSON object
	 * @throws Exception the exception
	 */
	public JSONObject addSubscriptionToTopic(String topicName,String endpointTyp, Map<String,Object> endpointMap) throws Exception {
		logger.info("Executing addSubscriptionToTopic for username: " + username);
		
		//authenticate the user and retrieve the token
		String token = TokenGenerator.getTokenForMessaging(username, apiKey);
		
		//generate the get queues URL		
				String url = URIGenerator.getSLMessagingAPIURLForTopic();
		
		//append the auth to the URL		
		url += "/" + topicName + "/subscriptions";
				
		XAuthTokenSLClient client = new XAuthTokenSLClient(token);
		ClientResponse clientResponse = client.executePOST(url, getJSON(endpointTyp,endpointMap));
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed  addSubscriptionTotopic for topicName: " + topicName + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 201 || clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug(" addSubscriptionTo topic: JSON Response: " + response);
			return json;
		}
		
		throw new Exception("Could not  addSubscription To topic: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);				
	}
	
	
	/**
	 * Gets the json.
	 *
	 * @param visibilityIntervals the visibility intervals
	 * @param expiration the expiration
	 * @param tags the tags
	 * @return the json
	 */
	private String getJSON(String endpointTyp,Map<String,Object> endpointMap) {
		
		System.out.println("obj json ===== "+new JSONObject(endpointMap));
		
		JSONObject json = new JSONObject();
		try {
			json.put("endpoint_type", endpointTyp);
			json.put("endpoint", new JSONObject(endpointMap));
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}		
		return json.toString();
	}
	
	
	
	

	
	
	
	

}
