package com.ibm.softlayer.notification.service;

import java.util.List;

import org.apache.wink.json4j.JSONObject;

import com.ibm.softlayer.util.APIConstants;

public class GetNotificationIdByNameService extends AbstractGetNotificationService{

	public GetNotificationIdByNameService(String username, String apikey) {
		super(username, apikey);	
	}		
	
	/**
	 * Gets the notifications.
	 *
	 * @param notificationId the notification id
	 * @return the notifications
	 * @throws Exception the exception
	 */
	public JSONObject getNotificationIdByName(String notificationName) throws Exception {
		return getByNotificationName(notificationName, APIConstants.GETALLOBJECT_API, null);
	}

	/**
	 * Gets the notification by id.
	 *
	 * @param notificationId the notification id
	 * @param objectMasks the object masks
	 * @return the notification by id
	 * @throws Exception the exception
	 */
	public JSONObject getNotificationById(String notificationName, List<String> objectMasks) throws Exception {
		return getByNotificationName(notificationName, APIConstants.GETALLOBJECT_API, objectMasks);
	}
}
