package com.ibm.softlayer.messaging.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.util.UnitTestConstants;

public class TopicServiceTest {

	/** The Constant random. */
	private static final long random = System.currentTimeMillis();
	
	/** The Constant topicName. */
	private static final String topicName = "Topic_" + random;
	
	/** The Constant topicTagName. */
	private static final String topicTagName = "TopicTag_" + random;
	
	/** The Constant queueName. */
	private static final String queueName = "Queue_" + random;
	
	/** The Constant queueTagName. */
	private static final String queueTagName = "QueueTag_" + random;
	
	public String subcriptionId;
	
	

	/**
	 * Test create queue.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCreateQueue() throws Exception {
		CreateQueueService service = new CreateQueueService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject queueCreated = service.createQueue(queueName, 10, 40000, Arrays.asList(queueTagName));
		assertNotNull(queueCreated);
		assertEquals(queueName, queueCreated.getString("name"));
	}
	
	/**
	 * Test create queue.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCreateTopic() throws Exception {
		CreateTopicService service = new CreateTopicService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject topicCreated = service.createTopic(topicName, Arrays.asList(topicTagName));
		assertNotNull(topicCreated);
		assertEquals(topicName, topicCreated.getString("name"));
	}
	
	
	/**
	 * Test Add Subscription to topic as Http.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testaddSubscriptionToTopicAsHttp() throws Exception {
		
		/**
		 * Endpoint As Http
		 * */
		String endPointType = "http";
		Map<String,Object> endpointMap = new HashMap<String, Object>();
	
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ignore", "true");
		
		Map<String,Object> headerMap = new HashMap<String, Object>();
		headerMap.put("Content-Type", "application/x-www-form-urlencoded");
		
		
		endpointMap.put("method", "GET");
		endpointMap.put("url", "http://softlayer.com");
		endpointMap.put("params", new JSONObject().put("ignore", "true"));
		endpointMap.put("headers", new JSONObject().put("Content-Type", "application/x-www-form-urlencoded"));
		endpointMap.put("body", "This is Message Body");
		
		
		/**
		 * Endpoint As Queue
		 * */
		//String endPointType = "queue";
		//Map<String,Object> endpointMap = new HashMap<String, Object>();
		//endpointMap.put("queue_name", queueName);
		
		
		
		AddSubscriptionToTopicService service = new AddSubscriptionToTopicService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject subscriptionAdded = service.addSubscriptionToTopic(topicName, endPointType, endpointMap);
		assertNotNull(subscriptionAdded);
		assertEquals("Object created", subscriptionAdded.get("message"));
		
		System.out.println("subscriptionAdded ===="+subscriptionAdded);
	}
	
	
	
	/**
	 * Test Add Subscription to topic as Queue.
	 *
	 * @throws Exception the exception
	 */
	
	@Test
	public void testaddSubscriptionToTopicAsQueue() throws Exception {
		
		/**
		 * Endpoint As Queue
		 * */
		String endPointType = "queue";
		Map<String,Object> endpointMap = new HashMap<String, Object>();
		endpointMap.put("queue_name", queueName);
		
		
		
		AddSubscriptionToTopicService service = new AddSubscriptionToTopicService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject subscriptionAdded = service.addSubscriptionToTopic(topicName, endPointType, endpointMap);
		assertNotNull(subscriptionAdded);
		assertEquals("Object created", subscriptionAdded.get("message"));
		
		System.out.println("subscriptionAdded ===="+subscriptionAdded);
	}
	
	
	
	@Test
	public void testListAllTopics() throws Exception {
		ListAllTopicsService service = new ListAllTopicsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY );
		JSONObject jsonObj = service.listAllTopics();
		assertNotNull(jsonObj);
		System.out.println(jsonObj.toString());
	}
	
	@Test
	public void testgetTopicDetails() throws Exception {
		GetTopicDetailsService service = new GetTopicDetailsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY );
		JSONObject jsonObj = service.getTopicDetails(topicName);
		assertNotNull(jsonObj);
		System.out.println(jsonObj.toString());
	}
	
	/**
	 * Test list subcriptions on topic.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testListAllSubscriptionsOnTopic() throws Exception {
		ListSubscriptionsOnTopic service = new ListSubscriptionsOnTopic(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY );
		JSONObject jsonObj = service.getAllSubcriptionOnTopic(topicName);
		assertNotNull(jsonObj);
		System.out.println("List all sub on topic : "+jsonObj.toString());
	}
	
	@Test
	public void testPushMessageToTopic() throws Exception {
		Map<String,String> fieldsMap = new HashMap<String, String>();
		fieldsMap.put("field1", "value1");
		fieldsMap.put("field2", "value2");
		
		PushMessageToTopicService service = new PushMessageToTopicService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject messagePushed = service.pushMessageToTopic(topicName, "Hello this is message body", 0 ,fieldsMap);
		assertNotNull(messagePushed);
		System.out.println("messagePushed ===="+messagePushed);
		
	}
	
	
	/**
	 * Test delete subcription.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDeleteSubcriptionFromTopic() throws Exception {
		
		ListSubscriptionsOnTopic service1 = new ListSubscriptionsOnTopic(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY );
		JSONObject jsonObj = service1.getAllSubcriptionOnTopic(topicName);
		assertNotNull(jsonObj);
		System.out.println("List all sub on topic to get sub id: "+jsonObj);
		JSONArray jsonarry = jsonObj.getJSONArray("items");
		JSONObject jsonOb = (JSONObject) jsonarry.get(0);
		this.subcriptionId = jsonOb.getString("id");
		
		System.out.println("subcriptionId ===="+this.subcriptionId);
		DeleteSubscriptionFromTopic service = new DeleteSubscriptionFromTopic(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		boolean deleted = service.deleteSubscriptionFromTopic(topicName,this.subcriptionId);
		assertEquals(deleted, true);
	}
	
	
	/**
	 * Test delete topic.
	 *
	 * @throws Exception the exception
	 */
	/*@Test
	public void testDeleteTopic() throws Exception {
		DeleteTopicService service = new DeleteTopicService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		boolean deleted = service.deleteTopic(topicName);;
		assertEquals(deleted, true);
	}*/
	
	

}
