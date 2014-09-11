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
		ArrayList<String> objectMaskList=new ArrayList<String>();
		objectMaskList.add("id");
		GetNotificationService service= new GetNotificationService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray notificationArray = service.getAllNotification(objectMaskList);
		assertNotNull(notificationArray);
		JSONObject notificationObject= notificationArray.getJSONObject(0);
		notificationId=notificationObject.getInt("id");
		//notificationName=notificationObject.getString("name");
	}
	
	@Test
	public void testgetNotificationByID() throws Exception{
		GetNotificationByIDService service= new GetNotificationByIDService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject notificationObject = service.getNotificationById(notificationId);
		assertNotNull(notificationObject);
	}
	
	/*@Test
	public void testgetNotificationBYName() throws Exception{
		GetNotificationIdByNameService service= new GetNotificationIdByNameService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject notificationObject = service.getNotificationIdByName(notificationName);
		assertNotNull(notificationObject);
	}
*/
	@Test
	public void testSubscribeForNotificationByID() throws Exception{
		SubscribeForNotificationService service= new SubscribeForNotificationService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject notificationObject = service.SubscribeForNotificationByID(notificationId, 248250);
		assertNotNull(notificationObject);
	}
	}

