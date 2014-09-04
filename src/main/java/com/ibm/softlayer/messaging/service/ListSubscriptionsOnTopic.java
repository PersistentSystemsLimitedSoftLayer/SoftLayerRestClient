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
import com.ibm.softlayer.util.APIConstants;

public class ListSubscriptionsOnTopic extends AbstractService{
	
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ListSubscriptionsOnTopic.class);	

	public ListSubscriptionsOnTopic(String username, String apiKey) {
		super(username, apiKey);
	}		
	
	
	
	
	public JSONObject getAllSubcriptionOnTopic(String topicName) throws Exception {
		logger.info("Executing getAllSubcriptionOnTopic for username: " + getUsername());
		
		//authenticate the user and retrieve the token
		String token = getAuthToken();
		
		//generate the get queues URL		
		String url = URIGenerator.getSLMessagingAPIURLForTopic();
		
		url += "/" + topicName + "/" + APIConstants.SUBSCRIPTION_API; 
		
		SoftLayerServiceClient client = new SoftLayerServiceClient(token);
		ClientResponse clientResponse = client.executeGET(url,null);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getAllSubcriptionOnTopic for username: " + getUsername() + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("getAllSubcriptionOnTopic: JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not retrieve SubcriptionOnTopic: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	
}
