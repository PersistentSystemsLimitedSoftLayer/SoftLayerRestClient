package com.ibm.softlayer.ticket.service;

import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class AbstractGetTicketDetails.
 */
public abstract class AbstractGetTicketsService {

	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AbstractGetTicketsService.class);
	
	/**
	 * Instantiates a new abstract get ticket details.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public AbstractGetTicketsService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
	}

	/**
	 * Gets the by ticket id.
	 *
	 * @param ticketId the ticket id
	 * @param ticketsURL the tickets url
	 * @param objectMasks the object masks
	 * @return the by ticket id
	 * @throws Exception the exception
	 */
	protected JSONObject getByTicketId(String ticketId, String ticketsURL, List<String> objectMasks) throws Exception 
	{
		logger.info("Executing getByTicketId:" + ticketsURL + " for ticketId: " + ticketId);
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.TICKETS_ROOT_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}		
		url.append(ticketId).append("/").append(ticketsURL);
		
		//setting the object masks
		processObjectMasks(url, objectMasks);
		
		//execute the get tickets call
		ClientResponse clientResponse = executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getByTicketId:" + ticketsURL + "  for ticketId: " + ticketId 
				+ ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);		
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("getByTicketId:" + ticketsURL + " JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve the getByTicketId:" + ticketsURL + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	/**
	 * Gets the array by ticket id.
	 *
	 * @param ticketId the ticket id
	 * @param ticketsURL the tickets url
	 * @param objectMasks the object masks
	 * @return the array by ticket id
	 * @throws Exception the exception
	 */
	protected JSONArray getArrayByTicketId(String ticketId, String ticketsURL, List<String> objectMasks) throws Exception 
	{
		logger.info("Executing getByTicketId:" + ticketsURL + " for ticketId: " + ticketId);
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.TICKETS_ROOT_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}		
		url.append(ticketId).append("/").append(ticketsURL);
		
		//setting the object masks
		processObjectMasks(url, objectMasks);
		
		//execute the get tickets call
		ClientResponse clientResponse = executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getByTicketId:" + ticketsURL + "  for ticketId: " + ticketId 
				+ ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);		
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("getByTicketId:" + ticketsURL + " JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve the getByTicketId:" + ticketsURL + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	/**
	 * Gets the tickets.
	 *
	 * @param ticketsURL the tickets url
	 * @param objectMasks the object masks
	 * @return the tickets
	 * @throws Exception the exception
	 */
	protected JSONArray getTickets(String ticketsURL, List<String> objectMasks) throws Exception 
	{
		logger.info("Executing getTickets: " + ticketsURL + " for username: " + username);
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.ACCOUNT_ROOT_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(ticketsURL);
		
		//setting the object masks
		processObjectMasks(url, objectMasks);
		
		//execute the get tickets call
		ClientResponse clientResponse = executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed get tickets for username: " + username + ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("getTickets: " + ticketsURL + " JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve getTickets: " + ticketsURL + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	/**
	 * Process object masks.
	 *
	 * @param url the url
	 * @param objectMasks the object masks
	 */
	private void processObjectMasks(StringBuffer url, List<String> objectMasks) {
		//setting the object masks
		if(objectMasks != null && objectMasks.size() > 0){
			StringBuffer mask = new StringBuffer();
			for(String maskVal : objectMasks) {
				if(mask.toString().trim().length() > 0){
					mask.append(";");
				}
				mask.append(maskVal);
			}
			
			//append the mask to the URL
			if(mask.toString().trim().length() > 0){
				url.append("?").append("objectMask=").append(mask.toString());
			}
		}
	}
	
	/**
	 * Execute get.
	 *
	 * @param url the url
	 * @return the client response
	 * @throws Exception the exception
	 */
	private ClientResponse executeGET(String url) throws Exception {
		//execute the get tickets call
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);	
		ClientResponse clientResponse = client.executeGET(url.toString());
		return clientResponse;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}		
}
