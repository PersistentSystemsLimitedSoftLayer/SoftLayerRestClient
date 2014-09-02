package com.ibm.softlayer.messaging.service;

import org.apache.wink.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

/**
 * The Class SendMessageToQueueService.
 */
public class DeleteMessageFromQueueService extends AbstractService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DeleteMessageFromQueueService.class);	

	
	/**
	 * Instantiates a new delete message from queue service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 * @param accountId the account id
	 */
	public DeleteMessageFromQueueService(String username, String apikey) {
		super(username, apikey);
	}

	
	/**
	 * Delete message from queue.
	 *
	 * @param queueName the queue name
	 * @param messageId the message id
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean deleteMessageFromQueue(String queueName, String messageId) throws Exception {
		logger.info("Executing deleteMessageFromQueue for queueName: " + queueName + ", messageId: " + messageId);
		
		//authenticate the user and retrieve the token
		String token = getAuthToken();
		
		//generate the get queues URL		
		String url = URIGenerator.getSLMessagingAPIURL();
		
		//append the queuename to the URL		
		url += "/" + queueName + "/" + APIConstants.MESSAGES_API + "/" + messageId;
		
		SoftLayerServiceClient client = new SoftLayerServiceClient(token);
		ClientResponse clientResponse = client.executeDELETE(url);
		String response = clientResponse.getEntity(String.class);
		if(clientResponse.getStatusCode() == 202) {
			return true;
		}
		
		throw new Exception("Could not delete Message from Queue: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}		
}
