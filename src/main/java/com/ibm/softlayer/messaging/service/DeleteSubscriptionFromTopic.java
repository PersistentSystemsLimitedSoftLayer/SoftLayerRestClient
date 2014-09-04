package com.ibm.softlayer.messaging.service;

import org.apache.wink.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

public class DeleteSubscriptionFromTopic extends AbstractService{
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DeleteSubscriptionFromTopic.class);	

	
	/**
	 * Instantiates a new delete SubscriptionFromTopic service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 * @param accountId the account id
	 */
	public DeleteSubscriptionFromTopic(String username, String apikey) {
		super(username, apikey);
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
		logger.info("Executing deleteSubscriptionFromTopic for topicName: " + topicName + ", subscriptionId: " + subscriptionId);
		
		//authenticate the user and retrieve the token
		String token = getAuthToken();
		
		//generate the get topics URL		
		String url = URIGenerator.getSLMessagingAPIURLForTopic();
		
		//append the topicname and subcription id to the URL		
		url += "/" + topicName + "/" + APIConstants.SUBSCRIPTION_API + "/" + subscriptionId;
		
		SoftLayerServiceClient client = new SoftLayerServiceClient(token);
		ClientResponse clientResponse = client.executeDELETE(url);
		String response = clientResponse.getEntity(String.class);
		if(clientResponse.getStatusCode() == 202) {
			return true;
		}
		
		throw new Exception("Could not delete subscription from topic: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}

}
