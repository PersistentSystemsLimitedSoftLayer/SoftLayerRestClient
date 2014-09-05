package com.ibm.softlayer.util;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.service.AuthenticationService;

/**
 * The Class StorageSoftLayerClient.
 */
public class TokenGenerator {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(TokenGenerator.class);	
	
	/** The properties. */
	private static SLProperties properties = SLProperties.getInstance();
	
	/**
	 * Gets the token for messaging.
	 *
	 * @param username the username
	 * @param apiKey the api key
	 * @return the token for messaging
	 * @throws Exception the exception
	 */
	public static String getTokenForMessaging(String username, String apiKey) throws Exception {				
		logger.debug("Executing authenticate for username: " + username);
		
		String authURL = URIGenerator.getURL(properties.getProperty(SLProperties.SL_MESSAGING_ACCOUNTID), APIConstants.AUTH_API);
		
		//call the service to authenticate
		AuthenticationService service = new AuthenticationService(username, apiKey);
		ClientResponse clientResponse = service.authenticatePOST(authURL);
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executing authenticate for username: " + username + ", Status Code: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			return (String) json.get("token");			
		}
		
		throw new Exception("Could not retrieve the token: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}	
	
	/**
	 * Gets the token for storage.
	 *
	 * @param tenant the tenant
	 * @param username the username
	 * @param apiKey the api key
	 * @return the token for storage
	 * @throws Exception the exception
	 */
	public static JSONObject getTokenForStorage(String tenant, String username, String apiKey) throws Exception {
		
		logger.debug("Executing getTokenForStorage for username: " + username + ", tenant: " + tenant);
		
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
		
		//call the service to authenticate
		AuthenticationService service = new AuthenticationService(username, apiKey);
		ClientResponse clientResponse = service.authenticateGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executed getTokenForStorage for username: " + username + ", Status Code: " + clientResponse.getStatusCode());
		
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
