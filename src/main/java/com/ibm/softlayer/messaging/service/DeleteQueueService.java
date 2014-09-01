package com.ibm.softlayer.messaging.service;

import org.apache.wink.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;

/**
 * The Class DeleteQueueService.
 */
public class DeleteQueueService extends AbstractService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DeleteQueueService.class);	
	
	/**
	 * Instantiates a new delete queue service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 * @param accountId the account id
	 */
	public DeleteQueueService(String username, String apikey) {
		super(username, apikey);
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
		String token = getAuthToken();
		
		//generate the get queues URL		
		String url = URIGenerator.getSLMessagingAPIURL();
		
		//append the auth to the URL		
		url += "/" + queueName;
		
		SoftLayerServiceClient client = new SoftLayerServiceClient(token);
		ClientResponse clientResponse = client.executeDELETE(url);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed deleteQueue for QueueName: " + queueName + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 202){
			return true;
		}
		
		throw new Exception("Could not delete Queue: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}
}
