package com.ibm.softlayer.messaging.service;

import static junit.framework.Assert.*;

import java.util.Arrays;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.util.UnitTestConstants;

/**
 * The Class QueueServiceTest.
 */
public class QueueServiceTest {
	
	/** The Constant random. */
	private static final long random = System.currentTimeMillis();
	
	/** The Constant queueName. */
	private static final String queueName = "Queue_" + random;
	
	/** The Constant queueTagName. */
	private static final String queueTagName = "QueueTag_" + random;

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
	 * Test get queues.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetQueues() throws Exception {
		GetQueuesService service = new GetQueuesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray array = service.getQueues();
		assertNotNull(array);		
	}		
	
	@Test
	public void testUpdateQueue() throws Exception {
		CreateQueueService service = new CreateQueueService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject queueUpdated = service.createQueue(queueName, 10, 80000, Arrays.asList(queueTagName));
		assertNotNull(queueUpdated);
		assertEquals(queueName, queueUpdated.getString("name"));
	}
	
	@Test
	public void testGetQueueByNameAfterUpdate() throws Exception {			
		GetQueueByNameService queueService = new GetQueueByNameService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject queueDetails = queueService.getQueue(queueName);
		assertNotNull(queueDetails);
		assertEquals(queueName, queueDetails.getString("name"));
		assertEquals("80000" , queueDetails.getString("expiration"));
	}		
	
	/**
	 * Test get queues with tags.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetQueuesWithTags() throws Exception {
		GetQueuesService service = new GetQueuesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray array = service.getQueues(queueTagName);
		assertNotNull(array);
		assertEquals(1, array.size());
	}
	
	@Test
	public void testGetQueuesWithMultipleTags() throws Exception {
		GetQueuesService service = new GetQueuesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray array = service.getQueues("testtagB, testtagC");
		assertNotNull(array);
		assertEquals(1, array.size());
	}		
	
	/**
	 * Test get queue by name_ not found.
	 *
	 * @throws Exception the exception
	 */
	@Test (expected = Exception.class)
	public void testGetQueueByName_NotFound() throws Exception {			
		GetQueueByNameService queueService = new GetQueueByNameService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject queueDetails = queueService.getQueue("TestQueue1234");
		assertNull(queueDetails);
	}
	
	@Test
	public void testDeleteQueue() throws Exception {
		DeleteQueueService service = new DeleteQueueService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		boolean deleted = service.deleteQueue(queueName);;
		assertEquals(deleted, true);
	}
	
//	@Test (expected = Exception.class)
//	public void testGetQueueByNameAfterDelete() throws Exception {			
//		//if the control reaches exception block means, queue is deleted. else, the test case should fail
//		GetQueueByNameService queueService = new GetQueueByNameService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY, UnitTestConstants.SL_ACCOUNTID);
//		queueService.getQueue(queueName);
//		Assert.fail();
//	}
}
