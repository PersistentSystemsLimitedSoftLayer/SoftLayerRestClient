package com.ibm.softlayer.messaging.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.XAuthTokenSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.TokenGenerator;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class SendMessageToQueueService.
 */
public class GetMessageFromQueueService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetMessageFromQueueService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new gets the message from queue service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 * @param accountId the account id
	 */
	public GetMessageFromQueueService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}	

	/**
	 * Pop message from queue.
	 *
	 * @param queueName the queue name
	 * @param messagesToPop the messages to pop
	 * @return the JSON array
	 * @throws Exception the exception
	 */
	public JSONArray popMessageFromQueue(String queueName, int messagesToPop) throws Exception {
		logger.info("Executing popMessageFromQueue for queueName: " + queueName + ", messagesToPop: " + messagesToPop);
		
		//authenticate the user and retrieve the token
		String token = TokenGenerator.getTokenForMessaging(username, apiKey);
		
		//generate the get queues URL		
		String url = URIGenerator.getSLMessagingAPIURL();
		
		//append the queuename to the URL		
		url += "/" + queueName + "/" + APIConstants.MESSAGES_API;
		
		//check if batch exists, add it as request params
		Map<String, String> requestParams = new HashMap<String, String>();
		if(messagesToPop > 1) {
			requestParams.put("batch", String.valueOf(messagesToPop));
		}
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(token);
		ClientResponse clientResponse = client.executeGET(url, requestParams);
		String response = clientResponse.getEntity(String.class);
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("popMessageFromQueue: JSON Response: " + response);
			return (JSONArray) json.get("items");			
		}
		
		throw new Exception("Could not pop Message from Queue: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}		
}
