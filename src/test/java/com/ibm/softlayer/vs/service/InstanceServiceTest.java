package com.ibm.softlayer.vs.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.Arrays;

import junit.framework.Assert;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.util.UnitTestConstants;

public class InstanceServiceTest {

	private static String hostname = "psl-" + String.valueOf(System.currentTimeMillis());
	private static String instanceId = null;
	
	@Test
	public void testGetAllVirtualServers() throws Exception {
		GetAllVirtalServers service = new GetAllVirtalServers(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray jsonArray = service.findAll();		
		assertNotNull(jsonArray);		
	}	
	
	@Test
	public void testGetAllVirtualServers_WithObjectMasks() throws Exception {
		GetAllVirtalServers service = new GetAllVirtalServers(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray jsonArray = service.findAll(Arrays.asList("id", "fullyQualifiedDomainName"));
		assertNotNull(jsonArray);
		instanceId = jsonArray.getJSONObject(0).getString("id");
	}	
	
	@Test
	public void testGetInstance() throws Exception {
		GetInstanceService service = new GetInstanceService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject jsonObject = service.getInstance(instanceId);
		assertNotNull(jsonObject);
		assertEquals(instanceId, jsonObject.getString("id"));
	}
	
//	@Test
//	public void testCreateInstance() throws Exception {
//		CreateInstanceService service = new CreateInstanceService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
//		JSONObject jsonObject = service.createInstance(hostname);
//		assertNotNull(jsonObject);
//		instanceId = jsonObject.getString("id");
//		assertEquals(hostname, jsonObject.getString("hostname"));
//	}	
//	
//	@Test
//	public void testGetInstance() throws Exception {
//		Thread.sleep(5000); // added sleep as these are async calls. we need some delay for the VMs to be created
//		//instanceId = "6061310";
//		GetInstanceService service = new GetInstanceService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
//		JSONObject jsonObject = service.getInstance(instanceId);
//		assertNotNull(jsonObject);
//		assertEquals(instanceId, jsonObject.getString("id"));
//	}
//	
//	@Test (expected = Exception.class)
//	public void testDeleteInstanceNullInstanceId() throws Exception {
//		DeleteInstanceService service = new DeleteInstanceService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
//		service.deleteInstance(null);
//		Assert.fail();
//	}	
//	
//	@Test
//	public void testDeleteInstance() throws Exception {
//		Thread.sleep(10000); // added sleep as these are async calls. we need some delay for the VMs to be created
//		//instanceId = "6061406";		
//		DeleteInstanceService service = new DeleteInstanceService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
//		boolean deleted = service.deleteInstance(instanceId);
//		assertEquals(true, deleted);
//	}	
}
