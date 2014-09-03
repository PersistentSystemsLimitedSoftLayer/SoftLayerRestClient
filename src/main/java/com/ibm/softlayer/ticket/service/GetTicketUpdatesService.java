package com.ibm.softlayer.ticket.service;

import java.util.List;

import org.apache.wink.json4j.JSONArray;

import com.ibm.softlayer.util.APIConstants;

/**
 * The Class GetTicketUpdatesService.
 */
public class GetTicketUpdatesService extends AbstractGetTicketsService {

	
	/**
	 * Instantiates a new gets the ticket updates service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public GetTicketUpdatesService(String username, String apikey) {
		super(username, apikey);		
	}
	
	/**
	 * Gets the ticket status.
	 *
	 * @param ticketId the ticket id
	 * @param objectMasks the object masks
	 * @return the ticket status
	 * @throws Exception the exception
	 */
	public JSONArray getTicketUpdates(String ticketId, List<String> objectMasks) throws Exception {
		return getArrayByTicketId(ticketId, APIConstants.GETUPDATES_API, objectMasks);
	}
	

	/**
	 * Gets the ticket status.
	 *
	 * @param ticketId the ticket id
	 * @return the ticket status
	 * @throws Exception the exception
	 */
	public JSONArray getTicketUpdates(String ticketId) throws Exception {
		return getArrayByTicketId(ticketId, APIConstants.GETUPDATES_API, null);
	}

}
