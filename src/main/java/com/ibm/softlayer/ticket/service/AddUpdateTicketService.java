package com.ibm.softlayer.ticket.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class AddUpdateTicketService.
 */
public class AddUpdateTicketService {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AddUpdateTicketService.class);
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new adds the update ticket service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public AddUpdateTicketService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
	}

	/**
	 * Adds the an update to ticket.
	 *
	 * @param ticketId the ticket id
	 * @param content the content
	 * @return the JSON array
	 * @throws Exception the exception
	 */
	public JSONArray addAnUpdateToTicket(String ticketId, String content) throws Exception {
		logger.info("Executing addAnUpdateToTicket ticketId: " + ticketId);
		
		//generate the create Tickets URL		
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.TICKETS_ROOT_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(ticketId).append("/addUpdate");
		
		//generate the JSON request
		JSONObject requestJson = new JSONObject();
		requestJson.put("entry", content);
		
		//add the vm details to the array
		JSONArray parameters = new JSONArray();
		parameters.add(requestJson);
		
		JSONObject requests = new JSONObject();
		requests.put("parameters", parameters);		
	
		//post the request
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);		
		ClientResponse clientResponse = client.executePOST(url.toString(), requests.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed create Ticket: clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200) {
			JSONArray json = new JSONArray(response);
			logger.debug("addAnUpdateToTicket: JSON Response: " + response);
			return json;
		}
		
		throw new Exception("Could not create Ticket: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);				
	}
}
