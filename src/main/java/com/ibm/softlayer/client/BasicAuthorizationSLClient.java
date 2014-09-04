package com.ibm.softlayer.client;

import org.apache.commons.codec.binary.Base64;

/**
 * The Class BasicAuthorizationSLClient.
 */
public class BasicAuthorizationSLClient extends AbstractSoftLayerClient {

	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	
	/**
	 * Instantiates a new basic authorization sl client.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public BasicAuthorizationSLClient(String username, String apikey) {			
		this.username = username;
		this.apiKey = apikey;
		setxAuthToken(new String (Base64.encodeBase64(getCredentialsColonSeperated().getBytes())));
		setUseBasicAuth(true);		
	}		
	
	
	/**
	 * Gets the credentials colon seperated.
	 *
	 * @return the credentials colon seperated
	 */
	public String getCredentialsColonSeperated() {
		return username + ":" + apiKey;
	}	
}
