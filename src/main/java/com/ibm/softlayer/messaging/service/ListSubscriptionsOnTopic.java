package com.ibm.softlayer.messaging.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import com.ibm.softlayer.client.XAuthTokenSLClient;
import com.ibm.softlayer.util.TokenGenerator;
import com.ibm.softlayer.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

public class ListSubscriptionsOnTopic {
	
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ListSubscriptionsOnTopic.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;

	public ListSubscriptionsOnTopic(String username, String apiKey) {
		this.username = username;
		this.apiKey = apiKey;	
	}		
	
	
	
	
	public JSONObject getAllSubcriptionOnTopic(String topicName) throws Exception {
		logger.info("Executing getAllSubcriptionOnTopic for username: " + username);
		
		//authenticate the user and retrieve the token
		String token = TokenGenerator.getTokenForMessaging(username, apiKey);
				
				//generate the get queues URL		
				String url = URIGenerator.getSLMessagingAPIURLForTopic();
		
		url += "/" + topicName + "/" + APIConstants.SUBSCRIPTION_API; 
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(token);
		ClientResponse clientResponse = client.executeGET(url,null);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getAllSubcriptionOnTopic for username: " + username + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("getAllSubcriptionOnTopic: JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not retrieve SubcriptionOnTopic: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	
}
