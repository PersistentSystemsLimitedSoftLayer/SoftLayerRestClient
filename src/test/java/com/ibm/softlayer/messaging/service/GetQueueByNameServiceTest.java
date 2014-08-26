package com.ibm.softlayer.messaging.service;

import static junit.framework.Assert.*;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.util.UnitTestConstants;

public class GetQueueByNameServiceTest {

	/**
	 * Test get token.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetQueueByName() throws Exception {
		
		//get all queues
		GetQueuesService service = new GetQueuesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY, UnitTestConstants.SL_ACCOUNTID);
		JSONArray array = service.getQueues();
		assertNotNull(array);		
		String queueName = array.getJSONObject(0).getString("name");
		
		//get the queue details by name, consider queue first in the above list
		GetQueueByNameService queueService = new GetQueueByNameService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY, UnitTestConstants.SL_ACCOUNTID);
		JSONObject queueDetails = queueService.getQueue(queueName);
		assertNotNull(queueDetails);
		assertEquals(queueName, queueDetails.getString("name"));
	}
}
