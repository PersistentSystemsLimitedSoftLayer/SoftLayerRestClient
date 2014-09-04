package com.ibm.softlayer.ticket.service;

import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class ListSubjectsService.
 */
public class ListSubjectsService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ListSubjectsService.class);

	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new creates the queue service.
	 * 
	 * @param username
	 *            the username
	 * @param apikey
	 *            the apikey
	 */

	public ListSubjectsService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
	}

	/**
	 * List_subjects.
	 *
	 * @return the JSON array
	 * @throws Exception the exception
	 */
	public JSONArray list_subjects() throws Exception {
		logger.info("Executing List Subject for username: " + username);
		
		String url = URIGenerator.getSLBaseURL(APIConstants.TICKETS_SUBJECTS_API);

		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);	
		ClientResponse clientResponse = client.executeGET(url+"/getAllObjects");

		String response = clientResponse.getEntity(String.class);
		
		logger.info("Executed list Subject for ticket service for Account: "
				 + "clientResponse: " + clientResponse.getStatusCode());

		if (clientResponse.getStatusCode() == 201
				|| clientResponse.getStatusCode() == 200) {
			JSONArray json = new JSONArray(response);
			logger.debug("List Subject: JSON Response: " + response);
			return json;
		}

		throw new Exception("Could not create List Subjects: Code: "
				+ clientResponse.getStatusCode() + ", Reason: " + response);

	}
	
	/**
	 * List_subjects by object mask.
	 *
	 * @param objectMask the object mask
	 * @return the JSON array
	 * @throws Exception the exception
	 */
	
	public JSONArray list_subjectsByObjectMask(List<String> objectMask) throws Exception {
		logger.info("Executing list Subject for username: " + username);

		String url = URIGenerator.getSLBaseURL(APIConstants.TICKETS_SUBJECTS_API);
		url += "/getAllObjects";
		StringBuffer buffer= new StringBuffer(url);
		processObjectMasks(buffer, objectMask);

		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);	
		ClientResponse clientResponse = client.executeGET(buffer.toString());

		String response = clientResponse.getEntity(String.class);
		
		logger.info("Executed list Subject for ticket service for Account: "
				 + "clientResponse: "
				+ clientResponse.getStatusCode());

		if (clientResponse.getStatusCode() == 201
				|| clientResponse.getStatusCode() == 200) {
			JSONArray json = new JSONArray(response);
			logger.debug("List Subject: JSON Response: " + response);
			return json;
		}

		throw new Exception("Could not create List Subjects: Code: "
				+ clientResponse.getStatusCode() + ", Reason: " + response);

	}
	
	/**
	 * Process object masks.
	 *
	 * @param url the url
	 * @param objectMasks the object masks
	 */
	private void processObjectMasks(StringBuffer url, List<String> objectMasks) {
		//setting the object masks
		if(objectMasks != null && objectMasks.size() > 0){
			StringBuffer mask = new StringBuffer();
			for(String maskVal : objectMasks) {
				if(mask.toString().trim().length() > 0){
					mask.append(";");
				}
				mask.append(maskVal);
			}
			
			//append the mask to the URL
			if(mask.toString().trim().length() > 0){
				url.append("?").append("objectMask=").append(mask.toString());
			}
		}
	}

}
