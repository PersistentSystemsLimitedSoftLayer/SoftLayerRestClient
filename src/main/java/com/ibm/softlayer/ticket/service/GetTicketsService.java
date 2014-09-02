package com.ibm.softlayer.ticket.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GetTicketsService.
 */
public class GetTicketsService extends AbstractService {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetTicketsService.class);	
	
	/** The Constant GET_OPEN_TICKETS. */
	private static final String GET_OPEN_TICKETS = "getOpenTickets";
	
	/** The Constant GET_CLOSED_TICKETS. */
	private static final String GET_CLOSED_TICKETS = "getClosedTickets";
	
	/** The Constant GET_TICKETS. */
	private static final String GET_TICKETS = "getTickets";
	
	/**
	 * Instantiates a new gets the tickets service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public GetTicketsService(String username, String apikey) {
		super(username, apikey);	
	}
	
	/**
	 * Gets the open tickets.
	 *
	 * @return the open tickets
	 * @throws Exception the exception
	 */
	public JSONArray getOpenTickets()throws Exception 
	{			
		return getTickets(GET_OPEN_TICKETS);
	}
	
	/**
	 * Gets the closed tickets.
	 *
	 * @return the closed tickets
	 * @throws Exception the exception
	 */
	public JSONArray getClosedTickets()throws Exception 
	{
		return getTickets(GET_CLOSED_TICKETS);		
	}
	
	/**
	 * Gets the all tickets.
	 *
	 * @return the all tickets
	 * @throws Exception the exception
	 */
	public JSONArray getAllTickets()throws Exception 
	{		
		return getTickets(GET_TICKETS);
	}
	
	/**
	 * Gets the tickets.
	 *
	 * @param ticketsURL the tickets url
	 * @return the tickets
	 * @throws Exception the exception
	 */
	private JSONArray getTickets(String ticketsURL) throws Exception 
	{
		logger.info("Executing "+ ticketsURL +" getAllTickets for username: " + getUsername());
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.ACCOUNT_ROOT_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(ticketsURL);
		
		//execute the get tickets call
		SoftLayerServiceClient client = new SoftLayerServiceClient();		
		ClientResponse clientResponse = client.executeGET(url.toString(), null, getCredentialsColonSeperated());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed get tickets for username: " + getUsername() + ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("GetTickets JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve the token: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
}
