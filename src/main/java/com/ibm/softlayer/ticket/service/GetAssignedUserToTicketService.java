package com.ibm.softlayer.ticket.service;

import java.util.List;

import org.apache.wink.json4j.JSONObject;

import com.ibm.softlayer.util.APIConstants;

public class GetAssignedUserToTicketService extends AbstractGetTicketsService {

	public GetAssignedUserToTicketService(String username, String apikey) {
		super(username, apikey);		
	}
	
	public JSONObject getAssignedUser(String ticketId, List<String> objectMasks) throws Exception {
		return getByTicketId(ticketId, APIConstants.GETASSIGNEDUSER_API, objectMasks);
	}
	

	public JSONObject getAssignedUser(String ticketId) throws Exception {
		return getByTicketId(ticketId, APIConstants.GETASSIGNEDUSER_API, null);
	}

}
