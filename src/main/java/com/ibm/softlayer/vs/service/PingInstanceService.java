package com.ibm.softlayer.vs.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class PingInstanceService.
 */
public class PingInstanceService extends AbstractVSService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(PingInstanceService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new ping instance service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public PingInstanceService(String username, String apikey) {
		super(username, apikey);
		this.username = username;
		this.apiKey = apikey;
	}

	
	/**
	 * Checks if is pinbbable.
	 *
	 * @param instanceId the instance id
	 * @return true, if is pinbbable
	 * @throws Exception the exception
	 */
	public boolean isPinbbable(String instanceId) throws Exception {
		//generate the get instance url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSoftLayerApiUrl(Arrays.asList(APIConstants.VIRTUAL_GUEST_ROOT_API, instanceId, APIConstants.IS_PINGABLE)));		
				
		String response = getString(url.toString());
		return Boolean.valueOf(response);
	}
	
	/**
	 * Check if instance active.
	 *
	 * @param instanceId the instance id
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean checkIfInstanceActive(String instanceId) throws Exception {
		logger.info("Checking if instance: " + instanceId +" becomes active...");
		
		int limit = 1;
		int checkLimit = 25;
		int sleepTime = 10;
		boolean instanceActive = false;				
		
		while(limit < checkLimit) {			
			try {
				//sleep for some time to ensure the instance creation request is initiated by softlayer
				Thread.sleep(sleepTime*1000);
				
				GetInstanceService service = new GetInstanceService(this.username, this.apiKey);
				System.out.println("getActiveTransaction: " + service.getActiveTransaction(instanceId));
				
				System.out.println("getProvisionDate: " + service.getProvisionDate(instanceId));
				
				System.out.println("getLastOperatingSystemReload: " + service.getLastOperatingSystemReload(instanceId));
				
				++limit;	
//				instanceActive = isPinbbable(instanceId);
//				if(!instanceActive) {
//					logger.info("Instance: " + instanceId +" is not yet active. Retrying in " + sleepTime + " seconds. Attemp " + limit + " of " + checkLimit);
//					++limit;									
//				}
//				else {				
//					break;				
//				}			
			}
			catch(Exception ex){
				logger.info("Instance: " + instanceId +" is not yet active. Retrying in " + sleepTime + " seconds. Attemp " + limit + " of " + checkLimit);
				++limit;
			}
		}
		
		if(instanceActive){
			return instanceActive;
		}
		
		throw new Exception("Instance: " + instanceId +" could not be active after " + checkLimit + " attempts.");
	}
}
