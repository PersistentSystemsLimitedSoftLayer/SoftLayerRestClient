package com.ibm.softlayer.notification.service;

import java.util.List;

import org.apache.wink.json4j.JSONArray;

import com.ibm.softlayer.util.APIConstants;

public class GetNotificationService extends AbstractGetNotificationService {

	public GetNotificationService(String username, String apikey) {
		super(username, apikey);
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * Gets All the notifications.
	 * @return the Array of notifications
	 * @throws Exception the exception
	 */

	public JSONArray getAllNotification() throws Exception
	{
	return getNotifications(APIConstants.GETALLOBJECT_API, null);
	}
	
	/**
	 * 
	 * Gets All the notification.
	 * @param objectMasks the object masks
	 * @return the Array of notifications
	 * @throws Exception the exception
	 */
	public JSONArray getAllNotification(List<String> objectMasks) throws Exception
	{
	return getNotifications(APIConstants.GETALLOBJECT_API, objectMasks);
			
	}
	
}
