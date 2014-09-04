package com.ibm.softlayer.storage.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.apache.wink.json4j.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.softlayer.storage.StorageAuthentication;
import com.ibm.softlayer.storage.object.service.ContainerService;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.UnitTestConstants;

public class ObjectStorageServiceTest {
	
	private static String authToken = null;
	private static String storageURL = null;
	private static String containerName = "container2";
	
	@BeforeClass
	public static void authenticateUser() throws Exception {
		StorageAuthentication auth = new StorageAuthentication();
		JSONObject authTokens = auth.getAuthToken(UnitTestConstants.STORAGE_TENANT_NAME, UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		assertNotNull(authTokens);
		assertNotNull(authTokens.get(APIConstants.X_AUTH_TOKEN));
		assertNotNull(authTokens.get(APIConstants.X_STORAGE_URL));
		authToken = authTokens.getString(APIConstants.X_AUTH_TOKEN);
		storageURL = authTokens.getString(APIConstants.X_STORAGE_URL);
	}

	@Test
	public void testCreateContainer() throws Exception {
		ContainerService service = new ContainerService(authToken);
		boolean created = service.createContainer(storageURL, containerName);
		assertEquals(true, created);
	}
	
	@Test
	public void testGetContainer() throws Exception {
		ContainerService service = new ContainerService(authToken);
		JSONObject container = service.getContainer(storageURL, containerName);
		assertEquals(0, container.size());
	}
	
	
}
