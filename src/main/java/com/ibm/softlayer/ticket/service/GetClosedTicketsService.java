package com.ibm.softlayer.ticket.service;

import java.util.List;

import org.apache.wink.json4j.JSONArray;

import com.ibm.softlayer.util.APIConstants;

/**
 * The Class GetClosedTicketsService.
 */
public class GetClosedTicketsService extends AbstractGetTicketsService {	
	
	/**
	 * Instantiates a new gets the closed tickets service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public GetClosedTicketsService(String username, String apikey) {
		super(username, apikey);	
	}
	
	
	/**
	 * Gets the closed tickets.
	 *
	 * @return the closed tickets
	 * @throws Exception the exception
	 */
	public JSONArray getClosedTickets()throws Exception {			
		return getTickets(APIConstants.GET_CLOSED_TICKETS, null);
	}		
	
	/**
	 * Gets the closed tickets.
	 *
	 * @param objectMasks the object masks
	 * @return the closed tickets
	 * @throws Exception the exception
	 */
	public JSONArray getClosedTickets(List<String> objectMasks)throws Exception {			
		return getTickets(APIConstants.GET_CLOSED_TICKETS, objectMasks);
	}
}
