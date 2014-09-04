package com.ibm.softlayer.images.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.messaging.service.CreateQueueService;
import com.ibm.softlayer.util.UnitTestConstants;

public class ImagesServiceTest {

	/** The Constant random. */
	private static final long random = System.currentTimeMillis();

	/**
	 * Test get Image.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testgetImageObject() throws Exception {
		String imageId="10599";
		GetImageDetailsService service = new GetImageDetailsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject imageJson = service.getImageObject(imageId);
		assertNotNull(imageJson);
		assertEquals(imageId, imageJson.getString("id"));
	}
	
	
	/**
	 * List public Images Test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testlistPublicImages() throws Exception {
		ListPublicImagesService service = new ListPublicImagesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject imageJson = service.listPublicImages();
		assertNotNull(imageJson);
	}
	
	

}
