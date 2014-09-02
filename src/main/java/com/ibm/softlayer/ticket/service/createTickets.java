


package com.ibm.softlayer.ticket.service;

import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;
import com.ibm.softlayer.messaging.service.GetQueuesService;
import com.ibm.softlayer.util.APIConstants;

public class createTickets  extends AbstractService{
	private static final Logger logger = LoggerFactory.getLogger(GetQueuesService.class);
	public createTickets(String username, String apikey) {
		super(username, apikey);	
	}

	public JSONObject createTickets(String AssignedToUser,String subjectId, String tittle,String content) throws Exception {
		logger.info("Executing Create ticket username: " + getUsername());
		
		//generate the get queues URL		
		String url = URIGenerator.getRestURL( APIConstants.TICKET_API);
		
		System.out.println("Printing URL "+ url+"/createStandardTicket.json");
				
		SoftLayerServiceClient client = new SoftLayerServiceClient();
		
		
		String templateObject =getJSON(AssignedToUser,subjectId,tittle, content);
	
		System.out.println("Printing Request Body to pass" +templateObject);
	
		ClientResponse clientResponse = client.executePOST(url+"/createStandardTicket.json", templateObject,getCredentialsColonSeperated());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed Create Tickets for Account");
		
		if(clientResponse.getStatusCode() == 201 || clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("Create Queue: JSON Response: " + response);
			return json;
		}
		
		throw new Exception("Could not create Queue: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);				
	}
	
	
	
	
private String getJSON(String AssignedToUser,String subjectId, String tittle,String content) throws Exception{
	JSONObject json1 = new JSONObject();
	
	json1.put("subjectId", subjectId);
	json1.put("assignedUserId", AssignedToUser);
	json1.put("title", tittle);
	JSONArray parameter = new JSONArray();
	parameter.add(json1);
	parameter.add(content);
	
	JSONObject json = new JSONObject();
	json.put("parameters", parameter);
	
			//logger.error(e.getMessage(), e);

		return json.toString() ;
	}
	
}
