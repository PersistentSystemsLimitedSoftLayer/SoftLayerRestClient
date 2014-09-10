package com.ibm.softlayer.vs.service;

import java.util.Arrays;

import org.apache.wink.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class DeleteInstanceService.
 */
public class DeleteInstanceService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DeleteInstanceService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new delete instance service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public DeleteInstanceService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
	}

	/**
	 * Delete instance.
	 *
	 * @param instanceId the instance id
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean deleteInstance(String instanceId) throws Exception {
		logger.info("Executing Delete Instance: " + instanceId);
		if(instanceId == null || instanceId.trim().length() == 0){
			throw new Exception("Instance Id is mandatory to delete the instance");
		}
		
		//generate the delete instance url
		String url = URIGenerator.getSoftLayerApiUrl(Arrays.asList(
				APIConstants.VIRTUAL_GUEST_ROOT_API, instanceId, APIConstants.DELETE_OBJECT_API));
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeDELETE(url);
		String response = clientResponse.getEntity(String.class);
		
		logger.info("Executed Delete Instance: " + instanceId + ", Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		return Boolean.valueOf(response);
	}
}
