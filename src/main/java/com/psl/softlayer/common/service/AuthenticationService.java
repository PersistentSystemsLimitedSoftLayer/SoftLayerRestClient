package com.psl.softlayer.common.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psl.softlayer.common.client.SoftLayerServiceClient;

/**
 * The Class AuthenticationService.
 */
public class AuthenticationService extends AbstractService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	
	/** The Constant instance. */
	private static final AuthenticationService instance = new AuthenticationService();
	
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
		
		StringBuffer authURL = getPublicBaseURL();
		if(!authURL.toString().endsWith("/")) {
			authURL.append("/");
		}
		
		//append the account id
		authURL.append(accountId).append("/");
		
		//append the auth to the URL
		authURL.append("auth");
		
		SoftLayerServiceClient client = new SoftLayerServiceClient();
		ClientResponse clientResponse = client.executePOST(authURL.toString(), username, apikey);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getAuthToken: Status Code: " + clientResponse.getStatusCode() + ", Response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			return (String) json.get("token");
		}
		
		throw new Exception("Could not retrieve the token: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}		
}
