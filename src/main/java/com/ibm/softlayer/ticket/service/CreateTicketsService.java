package com.ibm.softlayer.ticket.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

/**
 * The Class CreateTicketsService.
 */
public class CreateTicketsService  extends AbstractService {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CreateTicketsService.class);
	
	/**
	 * Instantiates a new creates the tickets service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public CreateTicketsService(String username, String apikey) {
		super(username, apikey);	
	}

	/**
	 * Creates the tickets.
	 *
	 * @param assignedToUser the assigned to user
	 * @param subjectId the subject id
	 * @param title the title
	 * @param content the content
	 * @return the JSON object
	 * @throws Exception the exception
	 */
	public JSONObject createTickets(String assignedToUser,String subjectId, String title, String content) throws Exception {
		logger.info("Executing Create ticket username: " + getUsername());
		
		//generate the create Tickets URL		
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.TICKETS_ROOT_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append("createStandardTicket");
		
		String templateObject = getJSON(assignedToUser,subjectId,title, content);
	
		SoftLayerServiceClient client = new SoftLayerServiceClient();		
		ClientResponse clientResponse = client.executePOST(url.toString(), templateObject, getCredentialsColonSeperated());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed create Ticket: clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("Create Ticket: JSON Response: " + response);
			return json;
		}
		
		throw new Exception("Could not create Ticket: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);				
	}		
	
	/**
	 * Gets the json.
	 *
	 * @param AssignedToUser the assigned to user
	 * @param subjectId the subject id
	 * @param tittle the tittle
	 * @param content the content
	 * @return the json
	 * @throws Exception the exception
	 */
	private String getJSON(String AssignedToUser,String subjectId, String tittle,String content) throws Exception {
		
		JSONObject json1 = new JSONObject();
		
		json1.put("subjectId", subjectId);
		json1.put("assignedUserId", AssignedToUser);
		json1.put("title", tittle);
		JSONArray parameter = new JSONArray();
		parameter.add(json1);
		parameter.add(content);
		
		JSONObject json = new JSONObject();
		json.put("parameters", parameter);
		
		return json.toString() ;
	}	
}
