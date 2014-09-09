package com.ibm.softlayer.notification.service;

import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

public abstract class AbstractGetNotificationService {
	
	
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AbstractGetNotificationService.class);
	
	/**
	 * Instantiates a new abstract get Notification details.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */

	public AbstractGetNotificationService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
	}
	
	/**
	 * Gets the by Notification id.
	 *
	 * @param notificationId the notification id
	 * @param notificationsURL the notifications url
	 * @param objectMasks the object masks
	 * @return the by notification id
	 * @throws Exception the exception
	 */
	protected JSONObject getByNotificationId(String notificationId, String NotificationURL, List<String> objectMasks) throws Exception 
	{
		logger.info("Executing getBynotificationId:" + NotificationURL + " for notificationId: " + notificationId);
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.NOTIFICATION_ROOT_URL));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}		
		url.append(notificationId).append("/").append(NotificationURL);
		
		//setting the object masks
		processObjectMasks(url, objectMasks);
		
		//execute the get notifications call
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getBynotificationId:" + NotificationURL + "  for notificationId: " + notificationId 
				+ ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);		
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("getBynotificationId:" + NotificationURL + " JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve the getByNotificationId:" + NotificationURL + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	
	/**
	 * Gets the notifications.
	 *
	 * @param notificationsURL the notifications url
	 * @param objectMasks the object masks
	 * @return the notifications
	 * @throws Exception the exception
	 */
	protected JSONArray getNotifications(String NotificationURL, List<String> objectMasks) throws Exception 
	{
		logger.info("Executing getnotifications: " + NotificationURL + " for username: " + username);
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.NOTIFICATION_ROOT_URL));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(NotificationURL);
		
		//setting the object masks
		processObjectMasks(url, objectMasks);
		
		//execute the get notifications call
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed get notifications for username: " + username + ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("getnotifications: " + NotificationURL + " JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve getnotifications: " + NotificationURL + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
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
