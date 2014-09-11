package com.ibm.softlayer.vs.service;

import java.util.Arrays;

import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class PingInstanceService.
 */
public class PingInstanceService extends AbstractVSService {

	/**
	 * Instantiates a new ping instance service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public PingInstanceService(String username, String apikey) {
		super(username, apikey);		
	}

	
	/**
	 * Checks if is pinbbable.
	 *
	 * @param instanceId the instance id
	 * @return true, if is pinbbable
	 * @throws Exception the exception
	 */
	public boolean isPingable(String instanceId) throws Exception {
		//generate the get instance url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSoftLayerApiUrl(Arrays.asList(APIConstants.VIRTUAL_GUEST_ROOT_API, instanceId, APIConstants.IS_PINGABLE)));		
				
		String response = getString(url.toString());
		return Boolean.valueOf(response);
	}	
}
