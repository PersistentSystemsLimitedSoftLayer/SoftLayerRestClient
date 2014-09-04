package com.ibm.softlayer.client;

import com.ibm.softlayer.util.AbstractSoftLayerClient;

/**
 * The Class XAuthTokenSLClient.
 */
public class XAuthTokenSLClient extends AbstractSoftLayerClient {	
	
	/**
	 * Instantiates a new x auth token sl client.
	 *
	 * @param token the token
	 */
	public XAuthTokenSLClient(String token) {			
		setxAuthToken(token);
		setUseAuthToken(true);
	}	
}
