package com.ibm.softlayer.storage;

import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.util.SLProperties;
import com.ibm.softlayer.util.APIConstants;

/**
 * The Class ObjectStorageAuthentication.
 */
public class StorageAuthentication {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(StorageAuthentication.class);
	
	/** The properties. */
	private SLProperties properties = SLProperties.getInstance();
	
	/**
	 * Gets the auth token.
	 *
	 * @param tenant the tenant
	 * @param username the username
	 * @param apikey the apikey
	 * @return the auth token
	 * @throws Exception the exception
	 */
	public JSONObject getAuthToken(String tenant, String username, String apikey) throws Exception {
		logger.debug("Executing getAuthToken for username: " + username);
		
		//generate the storage api
		StringBuffer url = new StringBuffer();
		url.append(properties.getProperty(SLProperties.SL_STORAGE_BASE_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		//append the auth api
		url.append(APIConstants.AUTH_API);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		//append the api version
		url.append(properties.getProperty(SLProperties.SL_STORAGE_BASE_API_VERSION));				
		
		JSONObject jsonObject = StorageSoftLayerClient.authenticate(url.toString(), tenant, username, apikey);
		logger.info("Executed getAuthToken: response: " + jsonObject);	
		return jsonObject;
	}
}
