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
import com.ibm.softlayer.images.service.GetIdFrmNamePublicPrivateService;
import com.ibm.softlayer.images.service.GetImageDetailsService;
import com.ibm.softlayer.images.service.ListPrivateImagesService;
import com.ibm.softlayer.images.service.ListPublicImagesService;
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
		//String imageId="230942";
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
	
	
	

	/**
	 * List private Images Test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testlistPrivateImages() throws Exception {
		ListPrivateImagesService service = new ListPrivateImagesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray imageJson =  service.listPrivateImages();
		assertNotNull(imageJson);
		System.out.println("Private Images ===="+imageJson);
		
	}
	
	
	/**
	 * Get Id From name public Images Test.
	 *
	 * @throws Exception the exception
	 */
	/*@Test
	public void testgetIdFromNamePublic() throws Exception {
		GetIdFromNamePublicService service = new GetIdFromNamePublicService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		String name = "name";
		String value = "test public";
		Map<String,Object> filterMap = new HashMap<String, Object>();		
		filterMap.put(name, new JSONObject().put("operation", value));
		String imageId =  service.getIdFromNamePublic(filterMap);
		assertNotNull(imageId);
		
		//String s = "https://api.softlayer.com/rest/v3/SoftLayer_Virtual_Guest_Block_Device_Template_Group/getPublicImages?objectFilter={"name":{"operation":"test public"}}";
		
	}
	*/
	

	/**
	 * Get Id From name public Images Test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testgetIdFromNamePublic() throws Exception {
		String name = "test public";
		GetIdFrmNamePublicPrivateService service = new GetIdFrmNamePublicPrivateService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		String imageId =  service.getIdFromNamePublic(name);
		assertNotNull(imageId);
		System.out.println("id is ===="+imageId);
	}
	
	/**
	 * Get Id From name public Images Test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testgetIdFromNamePrivate() throws Exception {
		String name = "test public";
		GetIdFrmNamePublicPrivateService service = new GetIdFrmNamePublicPrivateService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		String imageId =  service.getIdFromNamePrivate(name);
		//assertNotNull(imageId);
		System.out.println("id is ===="+imageId);
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
		Boolean isedited = service.editImageObject(imageId, bodyelementMap);
		assertEquals(true, isedited);;
		System.out.println("isedited is ===="+isedited);
	}
	
	
	
	

}
