package com.ibm.softlayer.notification.service;

import java.util.List;

import org.apache.wink.json4j.JSONObject;

import com.ibm.softlayer.util.APIConstants;

public class GetNotificationByIDService extends AbstractGetNotificationService {
	public GetNotificationByIDService(String username, String apikey) {
		super(username, apikey);	
	}		
	
	/**
	 * Gets the notifications.
	 *
	 * @param notificationId the notification id
	 * @return the notifications
	 * @throws Exception the exception
	 */
	public JSONObject getNotificationById(String notificationId) throws Exception {
		return getByNotificationId(notificationId, APIConstants.GETOBJECT_API, null);
	}

	/**
	 * Gets the notification by id.
	 *
	 * @param notificationId the notification id
	 * @param objectMasks the object masks
	 * @return the notification by id
	 * @throws Exception the exception
	 */
	public JSONObject getNotificationById(String notificationId, List<String> objectMasks) throws Exception {
		return getByNotificationId(notificationId, APIConstants.GETOBJECT_API, objectMasks);
	}

}
