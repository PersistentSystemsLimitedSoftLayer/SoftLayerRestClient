package com.ibm.softlayer.ticket.service;

import java.util.List;

import org.apache.wink.json4j.JSONObject;

import com.ibm.softlayer.util.APIConstants;

/**
 * The Class GetTicketStatusService.
 */
public class GetTicketStatusService extends AbstractGetTicketsService {

	/**
	 * Instantiates a new gets the ticket status service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public GetTicketStatusService(String username, String apikey) {
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
	public JSONObject getTicketStatus(String ticketId, List<String> objectMasks) throws Exception {
		return getByTicketId(ticketId, APIConstants.GETSTATUS_API, objectMasks);
	}
	
	/**
	 * Gets the ticket status.
	 *
	 * @param ticketId the ticket id
	 * @return the ticket status
	 * @throws Exception the exception
	 */
	public JSONObject getTicketStatus(String ticketId) throws Exception {
		return getByTicketId(ticketId, APIConstants.GETSTATUS_API, null);
	}

}
