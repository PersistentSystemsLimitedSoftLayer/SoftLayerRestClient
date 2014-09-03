package com.ibm.softlayer.messaging.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.util.UnitTestConstants;

/**
 * The Class QueueMessageServiceTest.
 */
public class QueueMessageServiceTest {

	/** The Constant random. */
	private static final long random = System.currentTimeMillis();
	
	/** The Constant queueName. */
	private static final String queueName = "Queue_" + random;
	
	/** The Constant queueTagName. */
	private static final String queueTagName = "QueueTag_" + random;
	
	/** The message id. */
	private static String messageId = null;
	
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
	 * Test send message to queue.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSendMessageToQueue() throws Exception {
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("maxSize", "10000");
		
		SendMessageToQueueService service = new SendMessageToQueueService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject messageSent = service.sendMessageToQueue(queueName, "Test Message", fields, 10, 40000);
		assertNotNull(messageSent);
		assertEquals("Test Message", messageSent.getString("body"));
		messageId = messageSent.getString("id");
	}
	
	/**
	 * Test pop message from queue.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPopMessageFromQueue() throws Exception {				
		GetMessageFromQueueService service = new GetMessageFromQueueService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray message = service.popMessageFromQueue(queueName, -1);
		assertNotNull(message);		
	}
	
	/**
	 * Test delete message from queue.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDeleteMessageFromQueue() throws Exception {				
		DeleteMessageFromQueueService service = new DeleteMessageFromQueueService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		boolean messagedeleted = service.deleteMessageFromQueue(queueName, messageId);
		assertEquals(messagedeleted, true);
	}
	
	/**
	 * Test delete queue.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDeleteQueue() throws Exception {
		DeleteQueueService service = new DeleteQueueService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		boolean deleted = service.deleteQueue(queueName);;
		assertEquals(deleted, true);
	}
}
