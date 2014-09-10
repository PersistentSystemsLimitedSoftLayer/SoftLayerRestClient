package com.ibm.softlayer.vs.service;

import java.util.Arrays;

import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class GetInstanceService.
 */
public class GetInstanceService extends AbstractVSService {

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
		url.append(URIGenerator.getSoftLayerApiUrl(Arrays.asList(APIConstants.VIRTUAL_GUEST_ROOT_API, instanceId, APIConstants.GETOBJECT_API)));		
		
		return get(url.toString());
	}		
	
	/**
	 * Gets the active transaction.
	 *
	 * @param instanceId the instance id
	 * @return the active transaction
	 * @throws Exception the exception
	 */
	public JSONObject getActiveTransaction(String instanceId) throws Exception {
		if(instanceId == null || instanceId.trim().length() == 0){
			throw new Exception("Instance Id is mandatory to get Active Transaction for an instance");
		}
		
		//generate the get instance url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSoftLayerApiUrl(Arrays.asList(
				APIConstants.VIRTUAL_GUEST_ROOT_API, instanceId, APIConstants.GETACTIVE_TRANSACTION_API)));		
		
		return get(url.toString());
	}
	
	/**
	 * Gets the provision date.
	 *
	 * @param instanceId the instance id
	 * @return the provision date
	 * @throws Exception the exception
	 */
	public String getProvisionDate(String instanceId) throws Exception {
		if(instanceId == null || instanceId.trim().length() == 0){
			throw new Exception("Instance Id is mandatory to get Provision Date for an instance");
		}
		
		//generate the get instance url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSoftLayerApiUrl(Arrays.asList(
				APIConstants.VIRTUAL_GUEST_ROOT_API, instanceId, APIConstants.GETPROVISION_DATE_API)));		
		
		return getString(url.toString());
	}
	
	/**
	 * Gets the last operating system reload.
	 *
	 * @param instanceId the instance id
	 * @return the last operating system reload
	 * @throws Exception the exception
	 */
	public String getLastOperatingSystemReload(String instanceId) throws Exception {
		if(instanceId == null || instanceId.trim().length() == 0){
			throw new Exception("Instance Id is mandatory to get Last Operating System Load for an instance");
		}
		
		//generate the get instance url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSoftLayerApiUrl(Arrays.asList(
				APIConstants.VIRTUAL_GUEST_ROOT_API, instanceId, APIConstants.GETLAST_OS_SYSTEM_LOAD_API)));		
		
		return getString(url.toString());
	}
}
