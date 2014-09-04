package com.ibm.softlayer.messaging.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.AbstractSoftLayerClient;
import com.ibm.softlayer.util.SLProperties;
import com.ibm.softlayer.util.SoftLayerAuthenticationService;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class StorageSoftLayerClient.
 */
public class MessagingSoftLayerClient extends AbstractSoftLayerClient {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(MessagingSoftLayerClient.class);
	
	/**
	 * Instantiates a new messaging soft layer client.
	 *
	 * @param token the token
	 */
	public MessagingSoftLayerClient(String token) {
		setxAuthToken(token);
		setUseAuthToken(true);
	}		
	
	/**
	 * Authenticate.
	 *
	 * @param username the username
	 * @param apiKey the api key
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String authenticate(String username, String apiKey) throws Exception {				
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
