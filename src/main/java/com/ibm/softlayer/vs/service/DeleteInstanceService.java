package com.ibm.softlayer.vs.service;

import org.apache.wink.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

/**
 * The Class DeleteInstanceService.
 */
public class DeleteInstanceService extends AbstractService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DeleteInstanceService.class);	
	
	/**
	 * Instantiates a new delete instance service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public DeleteInstanceService(String username, String apikey) {
		super(username, apikey);
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
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.VIRTUAL_GUEST_ROOT_API));
		url.append("/").append(instanceId);
				
		SoftLayerServiceClient client = new SoftLayerServiceClient();
		ClientResponse clientResponse = client.executeDELETE(url.toString(), getCredentialsColonSeperated());
		String response = clientResponse.getEntity(String.class);
		
		logger.info("Executed Delete Instance: " + instanceId + ", Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		return Boolean.valueOf(response);
	}
}
