package com.ibm.softlayer.common.service;

import com.ibm.softlayer.common.util.SLProperties;

/**
 * The Class AbstractService.
 */
public abstract class AbstractService {

	/** The username. */
	protected String username = null;
	
	/** The apikey. */
	protected String apikey = null;	
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the apikey.
	 *
	 * @return the apikey
	 */
	public String getApikey() {
		return apikey;
	}

	/**
	 * Sets the apikey.
	 *
	 * @param apikey the apikey to set
	 */
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}	
	
	
	/**
	 * Instantiates a new abstract service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public AbstractService(String username, String apikey) {
		this.username = username;
		this.apikey = apikey;		
	}	
	
	/**
	 * Gets the auth token.
	 *
	 * @return the auth token
	 * @throws Exception the exception
	 */
	public String getAuthToken() throws Exception {
		//authenticate the user and retrieve the token
		SLProperties properties = SLProperties.getInstance();		
		AuthenticationService authService = AuthenticationService.getInstance();
		return authService.getAuthToken(properties.getProperty(SLProperties.SL_MESSAGING_ACCOUNTID), username, apikey);
	}
	
	/**
	 * Gets the credentials colon seperated.
	 *
	 * @return the credentials colon seperated
	 */
	public String getCredentialsColonSeperated() {
		return getUsername()+":"+getApikey();
	}
}
