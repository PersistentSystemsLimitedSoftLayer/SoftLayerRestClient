package com.ibm.softlayer.util;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class StorageSoftLayerClient.
 */
public class TokenGenerator {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(TokenGenerator.class);		
	
	
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
		SLProperties properties = SLProperties.getInstance();		
		String authURL = URIGenerator.getURL(properties.getProperty(SLProperties.SL_MESSAGING_ACCOUNTID), APIConstants.AUTH_API);
		
		//call the service to authenticate
		SoftLayerAuthenticationService service = new SoftLayerAuthenticationService(username, apiKey);
		ClientResponse clientResponse = service.authenticatePOST(authURL);
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executing authenticate for username: " + username + ", Status Code: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			return (String) json.get("token");			
		}
		
		throw new Exception("Could not retrieve the token: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}	
}
