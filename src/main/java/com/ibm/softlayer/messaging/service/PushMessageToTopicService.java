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
import com.ibm.softlayer.util.APIConstants;

public class PushMessageToTopicService extends AbstractService{

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(PushMessageToTopicService.class);	
	
	/**
	 * 
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public PushMessageToTopicService(String username, String apikey) {
		super(username, apikey);		
	}
	
	
	public JSONObject pushMessageToTopic(String topicName,String bodyMessage, int visibilityDelay, Map<String,String> fields) throws Exception {
		logger.info("Executing pushMessageToTopic for username: " + getUsername());
		
		//authenticate the user and retrieve the token
		String token = getAuthToken();
		
		//generate the get pushMessageToTopic URL		
		String url = URIGenerator.getSLMessagingAPIURLForTopic();
		
		//append the auth to the URL		
		url += "/" + topicName + "/" + APIConstants.MESSAGES_API;
				
		SoftLayerServiceClient client = new SoftLayerServiceClient(token);
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
