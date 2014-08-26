package com.ibm.softlayer.common.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.util.URIGenerator;

/**
 * The Class AuthenticationService.
 */
public class AuthenticationService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	
	/** The Constant instance. */
	private static final AuthenticationService instance = new AuthenticationService();
	
	private static final String AUTH_API = "auth";
	
	/**
	 * Instantiates a new authentication service.
	 */
	private AuthenticationService() {
		
	}
	
	/**
	 * Gets the single instance of AuthenticationService.
	 *
	 * @return single instance of AuthenticationService
	 */
	public static AuthenticationService getInstance() {
		return instance;
	}
	
	/**
	 * Gets the auth token.
	 *
	 * @param accountId the account id
	 * @param username the username
	 * @param apikey the apikey
	 * @return the auth token
	 * @throws Exception the exception
	 */
	public String getAuthToken(String accountId, String username, String apikey) throws Exception {
		logger.info("Executing getAuthToken for Account: " + accountId + ", username: " + username);
		
		String authURL = URIGenerator.getURL(accountId, AUTH_API);
		
		SoftLayerServiceClient client = new SoftLayerServiceClient();
		ClientResponse clientResponse = client.authenticate(authURL, username, apikey);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getAuthToken: Status Code: " + clientResponse.getStatusCode() + ", Response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			return (String) json.get("token");
		}
		
		throw new Exception("Could not retrieve the token: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}		
}
