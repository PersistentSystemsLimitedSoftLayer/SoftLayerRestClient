package com.ibm.softlayer.ticket.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GetTicketByIdService.
 */
public class GetTicketByIdService extends AbstractService {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetTicketByIdService.class);
	
	
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
	public JSONObject getTicketById(String ticketId) throws Exception 
	{
		logger.info("Executing getTicketById for ticketId: " + ticketId);
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.TICKETS_ROOT_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append("getObject/id/").append(ticketId);
		
		//execute the get tickets call
		SoftLayerServiceClient client = new SoftLayerServiceClient();		
		ClientResponse clientResponse = client.executeGET(url.toString(), null, getCredentialsColonSeperated());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed get ticketById for username: " + getUsername() + ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("GetTicketById JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve the Ticket: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
}
