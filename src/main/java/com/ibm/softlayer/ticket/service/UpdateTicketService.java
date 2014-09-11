package com.ibm.softlayer.ticket.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

public class UpdateTicketService extends AbstractGetTicketsService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ListSubjectsService.class);

	
	public UpdateTicketService(String username, String apikey) {
		super(username, apikey);
	}

	/**
	 * 
	 * @param assinedToUser
	 * @param SubjectId
	 * @param tittle
	 * @param content
	 * @param ticketId
	 * @return 
	 * @throws Exception
	 */
	public JSONObject updateTicket (String assinedToUser, String SubjectId,String tittle,String content, String ticketId) throws Exception{
		
		GetTicketByIdService service = new GetTicketByIdService(getUsername(), getApiKey());
		JSONObject jsonObject = service.getTicketById(ticketId);
		
		/**
		 * removes earlier values in ticket
		 */
		jsonObject.remove(SubjectId);
		jsonObject.remove(assinedToUser);
		jsonObject.remove(tittle);
	
		/**
		 * adds new values to ticket
		 */
		
		jsonObject.put("subjectId", SubjectId);
		jsonObject.put("assignedUserId", assinedToUser);
		jsonObject.put("title", tittle);
		
		JSONArray parameter = new JSONArray();
		parameter.add(jsonObject);
		parameter.add(content);
		
		JSONObject json = new JSONObject();
		json.put("parameters", parameter);
		
		
        logger.info("Executing update ticket username: " + getUsername());
		
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.TICKETS_ROOT_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(ticketId);
		url.append("/edit.json");
		
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(getUsername(), getApiKey());		
		ClientResponse clientResponse = client.executePOST(url.toString(), json.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed update Ticket: clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json1 = new JSONObject(response);
			logger.debug("update Ticket: JSON Response: " + response);
			return json1;
			
		}
		
		throw new Exception("Could not update Ticket: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);		
				
	}
	

	
}
