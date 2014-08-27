package com.ibm.softlayer.vs.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;

/**
 * The Class GetInstanceService.
 */
public class GetInstanceService extends AbstractService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetInstanceService.class);	
	
	
	/**
	 * Instantiates a new gets the instance service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 * @param accountId the account id
	 */
	public GetInstanceService(String username, String apikey, String accountId) {
		super(username, apikey, accountId);
	}

	
	/**
	 * Gets the single instance of GetInstanceService.
	 *
	 * @param instanceId the instance id
	 * @return single instance of GetInstanceService
	 * @throws Exception the exception
	 */
	public JSONObject getInstance(String instanceId) throws Exception {
		logger.info("Executing Get Instance: " + instanceId);
		if(instanceId == null || instanceId.trim().length() == 0){
			throw new Exception("Instance Id is mandatory to retrieve the instance");
		}
		
		String url = "https://api.softlayer.com/rest/v3/SoftLayer_Virtual_Guest/6029176/getObject";				
		
		SoftLayerServiceClient client = new SoftLayerServiceClient();
		ClientResponse clientResponse = client.executeGET(url, null, getCredentialsColonSeperated());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed Get Instance: " + instanceId + ", Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("Get Instance: JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not retrieve the instance: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
}
