package com.ibm.softlayer.ticket.service;

import java.util.List;

import org.apache.wink.json4j.JSONObject;

import com.ibm.softlayer.util.APIConstants;

/**
 * The Class GetTicketByIdService.
 */
public class GetTicketByIdService extends AbstractGetTicketsService {
	
	/**
	 * Instantiates a new gets the ticket by id service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public GetTicketByIdService(String username, String apikey) {
		super(username, apikey);	
	}		
	
	/**
	 * Gets the tickets.
	 *
	 * @param ticketId the ticket id
	 * @return the tickets
	 * @throws Exception the exception
	 */
	public JSONObject getTicketById(String ticketId) throws Exception {
		return getByTicketId(ticketId, APIConstants.GETOBJECT_API, null);
	}

	/**
	 * Gets the ticket by id.
	 *
	 * @param ticketId the ticket id
	 * @param objectMasks the object masks
	 * @return the ticket by id
	 * @throws Exception the exception
	 */
	public JSONObject getTicketById(String ticketId, List<String> objectMasks) throws Exception {
		return getByTicketId(ticketId, APIConstants.GETOBJECT_API, objectMasks);
	}
}
