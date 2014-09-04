package com.ibm.softlayer.messaging.service;

import java.util.List;
import java.util.Map;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;

public class AddSubscriptionToTopicService extends AbstractService{
	
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AddSubscriptionToTopicService.class);	
	
	/**
	 * add subscription topic service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public AddSubscriptionToTopicService(String username, String apikey) {
		super(username, apikey);		
	}
	
	
	/**
	 * add subscription the topic.
	 *
	 * @param topicName the topic name
	 * @param 
	 * @param expiration the expiration
	 * @param tags the tags
	 * @return the JSON object
	 * @throws Exception the exception
	 */
	public JSONObject addSubscriptionToTopic(String topicName,String endpointTyp, Map<String,String> queueName) throws Exception {
		logger.info("Executing  addSubscriptionToTopic for username: " + getUsername());
		
		//authenticate the user and retrieve the token
		String token = getAuthToken();
		
		//generate the get  addSubscriptionToTopic URL		
		String url = URIGenerator.getSLMessagingAPIURLForTopic();
		
		//append the auth to the URL		
		url += "/" + topicName + "/subscriptions";
				
		SoftLayerServiceClient client = new SoftLayerServiceClient(token);
		ClientResponse clientResponse = client.executePOST(url, getJSON(endpointTyp,queueName));
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
	private String getJSON(String endpointTyp,Map<String,String> queueName) {
		
		System.out.println("obj json ===== "+new JSONObject(queueName));
		
		JSONObject json = new JSONObject();
		try {
			json.put("endpoint_type", endpointTyp);
			json.put("endpoint", new JSONObject(queueName));
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}		
		return json.toString();
	}
	
	

}
