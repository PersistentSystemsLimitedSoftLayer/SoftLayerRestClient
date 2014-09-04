package com.ibm.softlayer.storage;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.util.SLProperties;
import com.ibm.softlayer.util.APIConstants;

/**
 * The Class ObjectStorageAuthentication.
 */
public class StorageAuthentication {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(StorageAuthentication.class);
	
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
		logger.info("Executing getAuthToken for username: " + username);
		
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
		
		//adding the tenant to the username
		username = tenant + ":" + username;
		
		SoftLayerServiceClient client = new SoftLayerServiceClient();
		ClientResponse clientResponse = client.authenticateGET(url.toString(), username, apikey);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getAuthToken: Status Code: " + clientResponse.getStatusCode() + ", Response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			MultivaluedMap<String, String> headers = clientResponse.getHeaders();
			JSONObject json = new JSONObject();
			json.put(APIConstants.X_AUTH_TOKEN, headers.get(APIConstants.X_AUTH_TOKEN).get(0));
			json.put("X-Storage-Token", headers.get("X-Storage-Token").get(0));
			json.put(APIConstants.X_STORAGE_URL, headers.get(APIConstants.X_STORAGE_URL).get(0));
			logger.info("getAuthToken JSON Response: " + json);
			return json;
		}
		
		throw new Exception("Could not retrieve the token: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}
}
