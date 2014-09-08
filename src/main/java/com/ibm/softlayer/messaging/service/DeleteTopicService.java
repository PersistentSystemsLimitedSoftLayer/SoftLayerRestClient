package com.ibm.softlayer.messaging.service;

import org.apache.wink.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






import com.ibm.softlayer.client.XAuthTokenSLClient;
import com.ibm.softlayer.util.TokenGenerator;
import com.ibm.softlayer.util.URIGenerator;

public class DeleteTopicService {
	
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DeleteTopicService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new delete topic service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 * @param accountId the account id
	 */
	public DeleteTopicService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}

	/**
	 * Delete topic.
	 *
	 * @param topicName the topic name
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean deleteTopic(String topicName) throws Exception {
		logger.info("Executing deleteTopic for username: " + username);
		
		//authenticate the user and retrieve the token
		String token = TokenGenerator.getTokenForMessaging(username, apiKey);
				
				//generate the get queues URL		
				String url = URIGenerator.getSLMessagingAPIURLForTopic();
		
		//append the auth to the URL		
		url += "/" + topicName;
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(token);
		ClientResponse clientResponse = client.executeDELETE(url);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed deletetopic for topicName: " + topicName + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 202){
			return true;
		}
		
		throw new Exception("Could not delete topic: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}
	

}
