package com.ibm.softlayer.messaging.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;

/**
 * The Class GetQueueByNameService.
 */
public class GetQueueByNameService extends AbstractService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetQueueByNameService.class);		
	
	/**
	 * Instantiates a new gets the queue by name service.
	 *
	 * @param username the username
	 * @param apiKey the api key
	 */
	public GetQueueByNameService(String username, String apiKey) {
		super(username, apiKey);
	}	
	
	
	/**
	 * Gets the queue.
	 *
	 * @param queueName the queue name
	 * @return the queue
	 * @throws Exception the exception
	 */
	public JSONObject getQueue(String queueName) throws Exception {
		logger.info("Executing getQueue for queueName: " + queueName + ", username: " + username);
		
		//authenticate the user and retrieve the token
		String token = getAuthToken();
		
		//generate the get queues URL
		String url = URIGenerator.getSLMessagingAPIURL();
		
		//append the auth to the URL		
		url += "/" + queueName;
		
		SoftLayerServiceClient client = new SoftLayerServiceClient(token);
		ClientResponse clientResponse = client.executeGET(url, null);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getQueue for QueueName: " 
				+ queueName + ", username: " + username + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("GetQueue by Name: JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve Queue Details: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
}
