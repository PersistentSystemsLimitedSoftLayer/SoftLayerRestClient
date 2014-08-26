package com.ibm.softlayer.messaging.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;

public class GetQueueByNameService extends AbstractService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetQueueByNameService.class);		
	
	private static final String GET_QUEUES = "queues";
	
	public GetQueueByNameService(String username, String apiKey, String accountId) {
		super(username, apiKey, accountId);
	}	
	
	
	public JSONObject getQueue(String queueName) throws Exception {
		logger.info("Executing getQueue for Account: " + accountId + ", queueName: " + queueName + ", username: " + username);
		
		//authenticate the user and retrieve the token
		String token = getAuthToken();
		
		//generate the get queues URL
		String url = URIGenerator.getURL(accountId, GET_QUEUES);
		
		//append the auth to the URL		
		url += "/" + queueName;
		
		SoftLayerServiceClient client = new SoftLayerServiceClient();
		ClientResponse clientResponse = client.executeGET(url, token, null);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getQueue for Account: " + accountId + ", QueueName: " 
				+ queueName + ", username: " + username + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			return json;			
		}
		
		throw new Exception("Could not retrieve Queue Details: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
}
