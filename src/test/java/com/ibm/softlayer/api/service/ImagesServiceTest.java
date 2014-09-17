package com.ibm.softlayer.api.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.wink.json4j.JSONObject;
import org.apache.wink.json4j.JSONArray;
import org.junit.Test;

import com.ibm.softlayer.images.service.EditImageService;
import com.ibm.softlayer.images.service.GetImageDetailsService;
import com.ibm.softlayer.images.service.ListPrivateImagesService;
import com.ibm.softlayer.images.service.ListPublicImagesService;
import com.ibm.softlayer.util.UnitTestConstants;

public class ImagesServiceTest {	

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
		JSONArray imageJson =  service.listPublicImages();
		assertNotNull(imageJson);
	}
	
	@Test
	public void testlistPublicImagesByFilter() throws Exception {
		String imageName = "25G CentOS 5 64-bit";
		ListPublicImagesService service = new ListPublicImagesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray imageJson =  service.listPublicImages("name", imageName, null);		
		assertNotNull(imageJson);
		assertEquals(1, imageJson.size());
		assertEquals(imageName, imageJson.getJSONObject(0).getString("name"));
	}	
	
	@Test
	public void testGetIDFromPublicImagesByFilter() throws Exception {
		String imageName = "25G CentOS 5 64-bit";
		ListPublicImagesService service = new ListPublicImagesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray imageJson =  service.listPublicImages("name", imageName, Arrays.asList("id"));	
		assertNotNull(imageJson);
		assertEquals(1, imageJson.size());
		assertEquals(1, imageJson.getJSONObject(0).size());
		assertEquals("10006", imageJson.getJSONObject(0).getString("id"));
	}
	
	@Test
	public void testlistPrivateImages() throws Exception {
		ListPrivateImagesService service = new ListPrivateImagesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray imageJson =  service.listPrivateImages();
		assertNotNull(imageJson);
	}
	
	@Test
	public void testlistPrivateImagesByFilter() throws Exception {
		String imageName = "PslTestImage";
		ListPrivateImagesService service = new ListPrivateImagesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray imageJson =  service.listPrivateImages("name", imageName, null);		
		assertNotNull(imageJson);
		assertEquals(1, imageJson.size());
		assertEquals(imageName, imageJson.getJSONObject(0).getString("name"));
	}
	
	@Test
	public void testGetIDFromPrivateImagesByFilter() throws Exception {
		String imageName = "PslTestImage";
		ListPrivateImagesService service = new ListPrivateImagesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray imageJson =  service.listPrivateImages("name", imageName, Arrays.asList("id"));	
		assertNotNull(imageJson);
		assertEquals(1, imageJson.size());
		assertEquals(1, imageJson.getJSONObject(0).size());
		assertEquals("230942", imageJson.getJSONObject(0).getString("id"));
	}		
	
	
	/**
	 * Edit Images Test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testeditImageObject() throws Exception {
		String imageId = "230942";
		Map<String,String> bodyelementMap = new HashMap<String,String>();
		bodyelementMap.put("note", "This is edited mod");
		
		EditImageService service = new EditImageService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		boolean isedited = service.editImageObject(imageId, bodyelementMap);
		assertEquals(true, isedited);;
		System.out.println("isedited is ===="+isedited);
	}
}
