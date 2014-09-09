package com.ibm.softlayer.notification.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.awt.List;
import java.util.ArrayList;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.util.UnitTestConstants;


public class NotificationServiceTest {
	
	private static String notificationId = null;

	@Test
	public void testgetAllNotification() throws Exception{
		GetNotificationService service= new GetNotificationService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray notificationObject = service.getAllNotification();
		assertNotNull(notificationObject);
	}
	
	@Test
	public void testgetAllNotificationObjectMask() throws Exception{
		ArrayList<String> objectMaskList=new ArrayList<String>();
		objectMaskList.add("id");
		GetNotificationService service= new GetNotificationService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray notificationArray = service.getAllNotification(objectMaskList);
		assertNotNull(notificationArray);
		JSONObject notificationObject= notificationArray.getJSONObject(0);
		notificationId=notificationObject.getString("id");
	}
	
	@Test
	public void testgetNotificationByID() throws Exception{
		GetNotificationByIDService service= new GetNotificationByIDService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject notificationObject = service.getNotificationById(notificationId);
		assertNotNull(notificationObject);
	}
}

