package com.ibm.softlayer.messaging.service;

import org.apache.wink.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.XAuthTokenSLClient;
import com.ibm.softlayer.util.TokenGenerator;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class DeleteQueueService.
 */
public class DeleteQueueService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DeleteQueueService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new delete queue service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 * @param accountId the account id
	 */
	public DeleteQueueService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}

	/**
	 * Delete queue.
	 *
	 * @param queueName the queue name
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean deleteQueue(String queueName) throws Exception {
		logger.info("Executing deleteQueue for queueName: " + queueName);
		
		//authenticate the user and retrieve the token
		String token = TokenGenerator.getTokenForMessaging(username, apiKey);
		
		//generate the get queues URL		
		String url = URIGenerator.getSLMessagingAPIURL();
		
		//append the auth to the URL		
		url += "/" + queueName;
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(token);
		ClientResponse clientResponse = client.executeDELETE(url);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed deleteQueue for QueueName: " + queueName + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 202){
			return true;
		}
		
		throw new Exception("Could not delete Queue: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}
}
