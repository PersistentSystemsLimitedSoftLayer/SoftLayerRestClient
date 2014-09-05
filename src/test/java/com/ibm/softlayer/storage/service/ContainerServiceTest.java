package com.ibm.softlayer.storage.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.softlayer.storage.object.service.ContainerService;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.TokenGenerator;
import com.ibm.softlayer.util.UnitTestConstants;

public class ContainerServiceTest {
	
	private static String authToken = null;
	private static String storageURL = null;
	private static String containerName = "container11";
	private static final String containerMetaDataKey = "Maxdirectories";
	
	@BeforeClass
	public static void authenticateUser() throws Exception {
		JSONObject authTokens = TokenGenerator.getTokenForStorage(UnitTestConstants.STORAGE_TENANT_NAME, UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		assertNotNull(authTokens);
		assertNotNull(authTokens.get(APIConstants.X_AUTH_TOKEN));
		assertNotNull(authTokens.get(APIConstants.X_STORAGE_URL));
		authToken = authTokens.getString(APIConstants.X_AUTH_TOKEN);
		storageURL = authTokens.getString(APIConstants.X_STORAGE_URL);
	}

	@Test
	public void testCreateContainer() throws Exception {
		ContainerService service = new ContainerService(storageURL, authToken);
		boolean created = service.createContainer(containerName);
		assertEquals(true, created);
	}
	
	@Test
	public void testGetContainer() throws Exception {
		ContainerService service = new ContainerService(storageURL, authToken);
		JSONArray container = service.getContainer(containerName);
		assertEquals(0, container.size());
	}
	
	@Test
	public void testAddContainerMetaData() throws Exception {
		Map<String, String> metaData = new HashMap<String, String>();
		metaData.put(containerMetaDataKey, "10");
		metaData.put("Maxfilesize", "100");
		ContainerService service = new ContainerService(storageURL, authToken);
		boolean added = service.addContainerMetaData(containerName, metaData);
		assertEquals(true, added);
	}
	
	@Test
	public void testGetContainerMetaData() throws Exception {
		ContainerService service = new ContainerService(storageURL, authToken);
		JSONObject container = service.getContainerMetaData(containerName);
		assertNotNull(container);
		assertEquals(6, container.size());
		assertEquals("10", container.getString("X-Container-Meta-"+containerMetaDataKey));		
	}
	
	@Test
	public void testUpdateContainerMetaData() throws Exception {
		Map<String, String> metaData = new HashMap<String, String>();
		metaData.put(containerMetaDataKey, "20");
		ContainerService service = new ContainerService(storageURL, authToken);
		boolean added = service.addContainerMetaData(containerName, metaData);
		assertEquals(true, added);
	}
	
	@Test
	public void testGetContainerMetaDataAfterUpdate() throws Exception {
		ContainerService service = new ContainerService(storageURL, authToken);
		JSONObject container = service.getContainerMetaData(containerName);
		assertNotNull(container);
		assertEquals(6, container.size());
		assertEquals("20", container.getString("X-Container-Meta-"+containerMetaDataKey));		
	}
	
	@Test
	public void testRemoveContainerMetaData() throws Exception {
		Map<String, String> metaData = new HashMap<String, String>();
		metaData.put(containerMetaDataKey, "20");
		metaData.put("Maxfilesize", "");
		ContainerService service = new ContainerService(storageURL, authToken);
		boolean added = service.removeContainerMetaData(containerName, metaData);
		assertEquals(true, added);
	}
	
	@Test
	public void testGetContainerMetaDataAfterDelete() throws Exception {
		ContainerService service = new ContainerService(storageURL, authToken);
		JSONObject container = service.getContainerMetaData(containerName);
		assertNotNull(container);
		assertEquals(5, container.size());	
	}
	
	@Test
	public void testDeleteContainer() throws Exception {
		ContainerService service = new ContainerService(storageURL, authToken);
		boolean deleted = service.deleteContainer(containerName);
		assertEquals(true, deleted);
	}	
}
