package com.ibm.softlayer.ticket.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;
import com.ibm.softlayer.messaging.service.GetQueuesService;
import com.ibm.softlayer.util.APIConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class getTickets extends AbstractService {
	
	private static final Logger logger = LoggerFactory.getLogger(GetQueuesService.class);
	
	
	public getTickets(String username, String apikey) {
		super(username, apikey);	
	}
	
	public JSONArray getTickets(String call) throws Exception 
	{
		logger.info("Executing "+call+" getAllTickets for username: " + getUsername());
		
		//generate the get queues URL		
		String url = URIGenerator.getRestURL(APIConstants.ACCOUNT_API);
		System.out.println("after buliding url  "+url);
		//check if tags exists, add it as request params
		Map<String, String> requestParams = new HashMap<String, String>();
		
		
		SoftLayerServiceClient client = new SoftLayerServiceClient();
		
		ClientResponse clientResponse = client.executeGET(url+"/"+call,null, getCredentialsColonSeperated());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed get tickets for username: " + getUsername() + ", clientResponse: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("GetTickets JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve the token: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	public JSONArray getOpenTickets()throws Exception 
	{			
		String call="getOpenTickets";
		JSONArray response=getTickets(call);
		return response;		
	}
	public JSONArray getClosedTickets()throws Exception 
	{
		
		String call="getClosedTickets";
		JSONArray response=getTickets(call);
		return response;
		
	}

	public JSONArray getAllTickets()throws Exception 
	{
		
		String call="getTickets";
		JSONArray response=getTickets(call);
		return response;
		
	}

	public JSONObject getTicketByID(String id)throws Exception 
	{
		
		{
			logger.info("Executing get a Ticket by id for username: " + getUsername());
			
			//generate the get queues URL		
			String url = URIGenerator.getRestURL(APIConstants.TICKET_API);
			System.out.println("after buliding url  "+url);
			//check if tags exists, add it as request params
			Map<String, String> requestParams = new HashMap<String, String>();
			SoftLayerServiceClient client = new SoftLayerServiceClient();
			
			//Creating object mask
			requestParams.put("objectMask","groupId;priority;statusId;subjectId");
			
			ClientResponse clientResponse = client.executeGET(url+"/getObject/id/"+id,requestParams, getCredentialsColonSeperated());
			String response = clientResponse.getEntity(String.class);
			logger.info("Executed get tickets for username: " + getUsername() + ", clientResponse: " + clientResponse.getStatusCode());
			
			if(clientResponse.getStatusCode() == 200){
				JSONObject json = new JSONObject(response);
				logger.debug("GetTickets JSON Response: " + response);
				return json;			
			}
			
			throw new Exception("Could not retrieve the token: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
		}
	}


}
