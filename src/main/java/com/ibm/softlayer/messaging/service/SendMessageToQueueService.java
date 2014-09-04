package com.ibm.softlayer.messaging.service;

import java.util.Map;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONException;
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
public class SendMessageToQueueService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SendMessageToQueueService.class);	

	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new send message to queue service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 * @param accountId the account id
	 */
	public SendMessageToQueueService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
	}

	/**
	 * Send message to queue.
	 *
	 * @param queueName the queue name
	 * @param messageBody the message body
	 * @param fields the fields
	 * @param visibilityIntervals the visibility intervals
	 * @param visibility_delay the visibility_delay
	 * @return the JSON object
	 * @throws Exception the exception
	 */
	public JSONObject sendMessageToQueue(String queueName, String messageBody, 
			Map<String, String> fields, int visibilityIntervals, int visibility_delay) throws Exception {
		logger.info("Executing sendMessageToQueue for queueName: " + queueName + ", messageBody: " + messageBody);
		
		//authenticate the user and retrieve the token
		String token = TokenGenerator.getTokenForMessaging(username, apiKey);
		
		//generate the get queues URL		
		String url = URIGenerator.getSLMessagingAPIURL();
		
		//append the auth to the URL		
		url += "/" + queueName + "/" + APIConstants.MESSAGES_API;
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(token);
		ClientResponse clientResponse = client.executePOST(url, getJSON(messageBody, fields, visibilityIntervals, visibility_delay));
		String response = clientResponse.getEntity(String.class);
				
		if(clientResponse.getStatusCode() == 201){
			JSONObject json = new JSONObject(response);
			logger.debug("sendMessageToQueue by Name: JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not send Message to Queue: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}
	
	/**
	 * Gets the json.
	 *
	 * @param messageBody the message body
	 * @param fields the fields
	 * @param visibilityIntervals the visibility intervals
	 * @param visibility_delay the visibility_delay
	 * @return the json
	 */
	private String getJSON(String messageBody, Map<String, String> fields, int visibilityIntervals, int visibility_delay) {
		
		JSONObject json = new JSONObject();
		try {
			json.put("body", messageBody);
			json.put("fields", fields);
			json.put("visibility_delay", visibility_delay);
			json.put("visibility_interval", visibilityIntervals);			
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}		
		return json.toString();
	}
}
