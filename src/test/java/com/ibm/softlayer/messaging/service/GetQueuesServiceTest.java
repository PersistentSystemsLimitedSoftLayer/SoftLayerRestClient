package com.ibm.softlayer.messaging.service;

import static junit.framework.Assert.*;

import org.apache.wink.json4j.JSONArray;
import org.junit.Test;

import com.ibm.softlayer.util.UnitTestConstants;

public class GetQueuesServiceTest {

	/**
	 * Test get token.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetQueues() throws Exception {
		GetQueuesService service = new GetQueuesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY, UnitTestConstants.SL_ACCOUNTID);
		JSONArray array = service.getQueues();
		assertNotNull(array);
		assertEquals(4, array.size());
	}
	
	@Test
	public void testGetQueuesWithTags() throws Exception {
		GetQueuesService service = new GetQueuesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY, UnitTestConstants.SL_ACCOUNTID);
		JSONArray array = service.getQueues("sl2nd");
		assertNotNull(array);	
		assertEquals(3, array.size());
	}
	
	@Test
	public void testGetQueuesWithMultipleTags() throws Exception {
		GetQueuesService service = new GetQueuesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY, UnitTestConstants.SL_ACCOUNTID);
		JSONArray array = service.getQueues("testtagB, testtagC");
		assertNotNull(array);
		assertEquals(1, array.size());
	}
}
