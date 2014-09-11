package com.ibm.softlayer.vs.service;

import java.util.Arrays;
import java.util.List;

import org.apache.wink.json4j.JSONArray;

import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class GetAllVirtalServers.
 */
public class GetAllInstances extends AbstractVSService {

	/**
	 * Instantiates a new gets the all virtal servers.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public GetAllInstances(String username, String apikey) {
		super(username, apikey);
	}
	
	
	/**
	 * Find all.
	 *
	 * @return the JSON array
	 * @throws Exception the exception
	 */
	public JSONArray findAll() throws Exception {
		//generate the get instance url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSoftLayerApiUrl(Arrays.asList(
				APIConstants.ACCOUNT_ROOT_API, APIConstants.GET_VIRTUAL_GUESTS_API)));
			
		return findAll(url.toString());			
	}	

	/**
	 * Find all.
	 *
	 * @param objectMasks the object masks
	 * @return the JSON array
	 * @throws Exception the exception
	 */
	public JSONArray findAll(List<String> objectMasks) throws Exception {		
		//generate the get instance url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSoftLayerApiUrl(Arrays.asList(
				APIConstants.ACCOUNT_ROOT_API, APIConstants.GET_VIRTUAL_GUESTS_API)));
			
		return findAll(url.toString(), objectMasks);		
	}	
}
