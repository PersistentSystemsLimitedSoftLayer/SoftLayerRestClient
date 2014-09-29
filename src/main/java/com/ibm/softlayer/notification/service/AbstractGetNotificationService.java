package com.ibm.softlayer.notification.service;

import java.util.ArrayList;
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
	//private List<String> objectMasks = null ;
	
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
	protected JSONObject getByNotificationId(int notificationId, String NotificationURL, List<String> objectMasks) throws Exception 
	{
		logger.info("Executing getBynotificationId:" + NotificationURL + " for notificationId: " + notificationId);
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.NOTIFICATION_ROOT_URL));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}		
		url.append(notificationId).append("/").append(NotificationURL);
		
		//setting the object masks
		
		
		
		//execute the get notifications call
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString(),objectMasks);
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
	
		
		//execute the get notifications call
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString(),objectMasks);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed get notifications for username: " + username + ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("getnotifications: " + NotificationURL + " JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve getnotifications: " + NotificationURL + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	
	protected JSONObject createNotification(String notificationURL, int notificationId) throws Exception {
		logger.info("Executing Create Notification: " + notificationURL + " for username: " + username);
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.NOTIFICATION_USER_SUBSCRIBER_URL));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		
		//get user id of the current user
		int r= getUserIdbyUserName(username,apiKey);
		
		//execute the post notifications call
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username,apiKey);
		url.append(notificationURL+".json");
		String templateObject=getJSON(notificationId,r);
		ClientResponse clientResponse = client.executePOST(url.toString(),templateObject);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed User Subscription: clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		if(clientResponse.getStatusCode() == 201){
			JSONObject json = new JSONObject(response);
			logger.debug("Subscribe for a Notification: JSON Response: " + response);
			return json;
		}
		else if(response.contains("Duplicate subscribe"))
		{
			JSONObject json = new JSONObject(response);
			
			logger.error("Subscribe for a Notification: JSON Response: " + response);
			System.err.println("Subscribe for a Notification: JSON Response: " + response);
			return json;
		}
		
		throw new Exception("Could not Subscribe : Code: " + clientResponse.getStatusCode() + ", Reason: " + response);				
		

	}
	
	
	
	
	protected JSONArray getByNotificationName(String notificationName, String NotificationURL, List<String> objectMasks) throws Exception 
	{
		logger.info("Executing getBynotificationName:" + NotificationURL + " for notificationName: " + notificationName);
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.NOTIFICATION_ROOT_URL));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}		
		url.append(NotificationURL);
		
		//execute the get notifications call with Object Filter
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString(),null,"name",notificationName, objectMasks);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getBynotificationName:" + NotificationURL + "  for notification name: " + notificationName 
				+ ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);		
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("getBynotificationName:" + NotificationURL + " JSON Response: " + response);
			return json;			
		}
		throw new Exception("Could not found  : Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}
	
	
private String getJSON(int notificationId,int userId) throws Exception {	
		JSONObject json1 = new JSONObject();
		json1.put("notificationId", notificationId);
		json1.put("userRecordId",userId);
		JSONArray parameter=new JSONArray();
		parameter.add(json1);
		JSONObject json = new JSONObject();
		json.put("parameters", parameter);
		return json.toString() ;
	}


protected int getUserIdbyUserName(String userName, String apiKey) throws Exception
{
	
	List<String> objectMasks = new ArrayList<String>();
	
	objectMasks.add("id");
	System.out.println(objectMasks);
	StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.ACCOUNT_ROOT_API));
	if(!url.toString().endsWith("/")) {
		url.append("/");
	}
	url.append("getCurrentUser");
	
	
    BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(userName,apiKey);
	ClientResponse clientResponse = client.executeGET(url.toString(),objectMasks);
	String response = clientResponse.getEntity(String.class);
	JSONObject json = new JSONObject(response);
	System.out.println("response************* "+json.getInt("id"));
	return (json.getInt("id"));

}


}


