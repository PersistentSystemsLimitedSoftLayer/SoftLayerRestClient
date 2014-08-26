package com.ibm.softlayer.common.service;

/**
 * The Class AbstractService.
 */
public abstract class AbstractService {

	/** The username. */
	protected String username = null;
	
	/** The apikey. */
	protected String apikey = null;
	
	/** The account id. */
	protected String accountId = null;

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
	 * Gets the account id.
	 *
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	/**
	 * Instantiates a new abstract service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 * @param accountId the account id
	 */
	public AbstractService(String username, String apikey, String accountId) {
		this.username = username;
		this.apikey = apikey;
		this.accountId = accountId;
	}	
	
	/**
	 * Gets the auth token.
	 *
	 * @return the auth token
	 * @throws Exception the exception
	 */
	public String getAuthToken() throws Exception {
		//authenticate the user and retrieve the token
		AuthenticationService authService = AuthenticationService.getInstance();
		return authService.getAuthToken(accountId, username, apikey);
	}
}
