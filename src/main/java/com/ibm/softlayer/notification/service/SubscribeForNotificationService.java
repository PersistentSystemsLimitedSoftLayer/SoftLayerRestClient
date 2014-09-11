package com.ibm.softlayer.notification.service;

import org.apache.wink.json4j.JSONObject;

import com.ibm.softlayer.util.APIConstants;

public class SubscribeForNotificationService  extends AbstractGetNotificationService{

	
	public SubscribeForNotificationService(String username, String apikey) {
		super(username, apikey);
	}
	
	public JSONObject SubscribeForNotificationByID(int NotificationId ,int userId) throws Exception
	{
	return createNotification(APIConstants.CREATEOBJECT_API, NotificationId, userId);
	}
	
}
