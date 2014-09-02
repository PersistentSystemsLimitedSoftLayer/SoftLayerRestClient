package com.ibm.softlayer.vs.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

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
	 */
	public GetInstanceService(String username, String apikey) {
		super(username, apikey);
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
		
		//generate the get instance url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.VIRTUAL_GUEST_ROOT_API));
		url.append("/").append(instanceId).append("/getObject");
				
		SoftLayerServiceClient client = new SoftLayerServiceClient();
		ClientResponse clientResponse = client.executeGET(url.toString(), null, getCredentialsColonSeperated());
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
