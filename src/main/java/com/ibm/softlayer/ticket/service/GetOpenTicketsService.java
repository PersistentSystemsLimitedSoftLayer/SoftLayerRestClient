package com.ibm.softlayer.ticket.service;

import java.util.List;

import org.apache.wink.json4j.JSONArray;

import com.ibm.softlayer.util.APIConstants;

/**
 * The Class GetOpenTicketsService.
 */
public class GetOpenTicketsService extends AbstractGetTicketsService {	
	
	/**
	 * Instantiates a new gets the open tickets service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public GetOpenTicketsService(String username, String apikey) {
		super(username, apikey);	
	}
	
	
	/**
	 * Gets the open tickets.
	 *
	 * @return the open tickets
	 * @throws Exception the exception
	 */
	public JSONArray getOpenTickets()throws Exception {			
		return getTickets(APIConstants.GET_OPEN_TICKETS, null);
	}		
	
	/**
	 * Gets the open tickets.
	 *
	 * @param objectMasks the object masks
	 * @return the open tickets
	 * @throws Exception the exception
	 */
	public JSONArray getOpenTickets(List<String> objectMasks)throws Exception {			
		return getTickets(APIConstants.GET_OPEN_TICKETS, objectMasks);
	}
}
