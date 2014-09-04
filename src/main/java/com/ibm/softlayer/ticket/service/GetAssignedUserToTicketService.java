package com.ibm.softlayer.ticket.service;

import java.util.List;

import org.apache.wink.json4j.JSONObject;

import com.ibm.softlayer.util.APIConstants;

/**
 * The Class GetAssignedUserToTicketService.
 */
public class GetAssignedUserToTicketService extends AbstractGetTicketsService {

	/**
	 * Instantiates a new gets the assigned user to ticket service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public GetAssignedUserToTicketService(String username, String apikey) {
		super(username, apikey);		
	}
	
	/**
	 * Gets the assigned user.
	 *
	 * @param ticketId the ticket id
	 * @param objectMasks the object masks
	 * @return the assigned user
	 * @throws Exception the exception
	 */
	public JSONObject getAssignedUser(String ticketId, List<String> objectMasks) throws Exception {
		return getByTicketId(ticketId, APIConstants.GETASSIGNEDUSER_API, objectMasks);
	}
	

	/**
	 * Gets the assigned user.
	 *
	 * @param ticketId the ticket id
	 * @return the assigned user
	 * @throws Exception the exception
	 */
	public JSONObject getAssignedUser(String ticketId) throws Exception {
		return getByTicketId(ticketId, APIConstants.GETASSIGNEDUSER_API, null);
	}

}
