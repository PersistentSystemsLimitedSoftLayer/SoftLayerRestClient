package com.ibm.softlayer.storage.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.apache.wink.json4j.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.softlayer.storage.object.service.ContainerService;
import com.ibm.softlayer.storage.object.service.FileObjectService;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.TokenGenerator;
import com.ibm.softlayer.util.UnitTestConstants;

public class FileObjectServiceTest {

	private static String authToken = null;
	private static String storageURL = null;
	private static String containerName = "container12";
	private static String uploadFileName = "logs.zip";
	private static int uploadFileName_size = 814; 
	
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
	
	/*@Test
	public void testUploadFileToContainer() throws Exception {
		String fileLocation = Thread.currentThread().getContextClassLoader().getResource(uploadFileName).getFile();
		FileObjectService service = new FileObjectService(storageURL, authToken, containerName);
		boolean uploaded = service.uploadFile(fileLocation);
		assertEquals(true, uploaded);
	}
	
	@Test
	public void testGetUploadFileFromContainer() throws Exception {
		FileObjectService service = new FileObjectService(storageURL, authToken, containerName);
		byte[] bytes = service.getFileObject(uploadFileName);
		assertEquals(uploadFileName_size, bytes.length);
	}
	
	@Test
	public void testDeleteUploadFileFromContainer() throws Exception {
		FileObjectService service = new FileObjectService(storageURL, authToken, containerName);
		boolean retrieved = service.deleteFileObject(uploadFileName);
		assertEquals(true, retrieved);
	}*/
	
	@Test
	public void testDeleteContainer() throws Exception {
		ContainerService service = new ContainerService(storageURL, authToken);
		boolean deleted = service.deleteContainer(containerName);
		assertEquals(true, deleted);
	}	
}
