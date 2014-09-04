package com.ibm.softlayer.ticket.service;

import org.apache.commons.codec.binary.Base64;

import com.ibm.softlayer.util.AbstractSoftLayerClient;

/**
 * The Class TicketSoftLayerClient.
 */
public class TicketSoftLayerClient extends AbstractSoftLayerClient {

	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new ticket soft layer client.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public TicketSoftLayerClient(String username, String apikey) {			
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
