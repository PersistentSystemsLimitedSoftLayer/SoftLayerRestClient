package com.ibm.softlayer.vs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class WaitInstanceToBeReadyService.
 */
public class WaitInstanceToBeReadyService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(WaitInstanceToBeReadyService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new wait instance to be ready service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public WaitInstanceToBeReadyService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
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
		int sleepTime = 15;
		boolean instanceActive = false;				
		
		GetInstanceService service = new GetInstanceService(this.username, this.apiKey);
		while(limit <= checkLimit) {			
			try {
				//sleep for some time to ensure the instance creation request is initiated by softlayer
				Thread.sleep(sleepTime*1000);
				
				String activeTrans = service.getInstanceRelationalInfo(instanceId, "activeTransaction");							
				if(activeTrans != null && activeTrans.trim().length() > 0 && !activeTrans.equals("null")) {
					logger.info("Instance: " + instanceId +" is not yet active. Retrying in " + sleepTime + " seconds. Attemp " + limit + " of " + checkLimit);
					++limit;									
				}
				else {	
					instanceActive = true;
					break;				
				}			
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
