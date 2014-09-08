package com.ibm.softlayer.messaging.service;

import org.apache.wink.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;







import com.ibm.softlayer.client.XAuthTokenSLClient;
import com.ibm.softlayer.util.TokenGenerator;
import com.ibm.softlayer.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

public class DeleteSubscriptionFromTopic {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DeleteSubscriptionFromTopic.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;

	
	/**
	 * Instantiates a new delete SubscriptionFromTopic service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 * @param accountId the account id
	 */
	public DeleteSubscriptionFromTopic(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}

	
	/**
	 * Delete SubscriptionFromTopic.
	 *
	 * @param topicName the topic name
	 * @param subscriptionId the subscription id
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean deleteSubscriptionFromTopic(String topicName, String subscriptionId) throws Exception {
		logger.info("Executing deleteSubscriptionFromTopic for username: " + username);
		
		//authenticate the user and retrieve the token
		String token = TokenGenerator.getTokenForMessaging(username, apiKey);
				
				//generate the get queues URL		
				String url = URIGenerator.getSLMessagingAPIURLForTopic();
		
		//append the topicname and subcription id to the URL		
		url += "/" + topicName + "/" + APIConstants.SUBSCRIPTION_API + "/" + subscriptionId;
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(token);
		ClientResponse clientResponse = client.executeDELETE(url);
		String response = clientResponse.getEntity(String.class);
		if(clientResponse.getStatusCode() == 202) {
			return true;
		}
		
		throw new Exception("Could not delete subscription from topic: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}

}
