package com.ibm.softlayer.ticket.service;

import java.util.List;

import org.apache.wink.json4j.JSONArray;

import com.ibm.softlayer.util.APIConstants;

/**
 * The Class GetTicketsService.
 */
public class GetTicketsService extends AbstractGetTicketsService {
	
	
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
	 * Gets the all tickets.
	 *
	 * @return the all tickets
	 * @throws Exception the exception
	 */
	public JSONArray getAllTickets()throws Exception {		
		return getTickets(APIConstants.GET_TICKETS, null);
	}		

	/**
	 * Gets the all tickets.
	 *
	 * @param objectMasks the object masks
	 * @return the all tickets
	 * @throws Exception the exception
	 */
	public JSONArray getAllTickets(List<String> objectMasks)throws Exception {		
		return getTickets(APIConstants.GET_TICKETS, objectMasks);
	}		

}
