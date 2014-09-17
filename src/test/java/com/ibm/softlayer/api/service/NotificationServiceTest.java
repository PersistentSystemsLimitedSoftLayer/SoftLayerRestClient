package com.ibm.softlayer.api.service;

import static junit.framework.Assert.assertNotNull;
import java.util.Arrays;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.notification.service.GetNotificationByIDService;
import com.ibm.softlayer.notification.service.GetNotificationIdByNameService;
import com.ibm.softlayer.notification.service.GetNotificationService;
import com.ibm.softlayer.notification.service.SubscribeForNotificationService;
import com.ibm.softlayer.util.UnitTestConstants;


public class NotificationServiceTest {
	
	private static int notificationId = 0;
	private static String notificationName=null;

	@Test
	public void testgetAllNotification() throws Exception{
		GetNotificationService service= new GetNotificationService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray notificationArray = service.getAllNotification();
		assertNotNull(notificationArray);
		JSONObject notificationObject1= notificationArray.getJSONObject(0);
		notificationId=notificationObject1.getInt("id");
		notificationName=notificationObject1.getString("name");
	}
	
	@Test
	public void testgetAllNotificationObjectMask() throws Exception{
		GetNotificationService service= new GetNotificationService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray notificationArray = service.getAllNotification(Arrays.asList("id", "name"));
		assertNotNull(notificationArray);
		JSONObject notificationObject= notificationArray.getJSONObject(0);
		notificationId=notificationObject.getInt("id");
		
	}
	
	@Test
	public void testgetNotificationByID() throws Exception{
		GetNotificationByIDService service= new GetNotificationByIDService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject notificationObject = service.getNotificationById(notificationId);
		assertNotNull(notificationObject);
	}
	
	@Test
	public void testgetNotificationBYNameFilter() throws Exception{
		GetNotificationIdByNameService service= new GetNotificationIdByNameService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray notificationObject = service.getNotificationIdByName(notificationName);
		assertNotNull(notificationObject);
	}

	@Test
	public void testSubscribeForNotificationByID() throws Exception{
		SubscribeForNotificationService service= new SubscribeForNotificationService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject notificationObject = service.SubscribeForNotificationByID(notificationId);
		assertNotNull(notificationObject);
	}
	}

