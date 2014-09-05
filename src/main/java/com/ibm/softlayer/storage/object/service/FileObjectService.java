package com.ibm.softlayer.storage.object.service;

import java.io.File;

import org.apache.wink.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.XAuthTokenSLClient;

/**
 * The Class FileObjectService.
 */
public class FileObjectService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(FileObjectService.class);
	
	/** The auth token. */
	private String authToken = null;
	
	/** The storage account url. */
	private String storageAccountURL = null;
	
	/** The container name. */
	private String containerName = null;	
	
	/** The file object base url. */
	private String fileObjectBaseURL = null;
	
	/**
	 * Instantiates a new file object service.
	 *
	 * @param storageURL the storage url
	 * @param token the token
	 * @param containerName the container name
	 */
	public FileObjectService (String storageURL, String token, String containerName) {
		this.authToken = token;
		this.storageAccountURL = storageURL;
		this.containerName = containerName;
		
		//generate the storage URL
		StringBuffer url = new StringBuffer(storageAccountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(containerName);
		fileObjectBaseURL = url.toString();
	}
	
	/**
	 * Upload file.
	 *
	 * @param fileLocation the file location
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean uploadFile(String fileLocation) throws Exception {
		
		logger.debug("Executing uploadFile for containerName: " + containerName + ", fileLocation: " + fileLocation);
		
		//check if file exists
		File fileObj = new File(fileLocation);
		if(!fileObj.exists()) {
			throw new Exception("Data File: " + fileLocation + " does not exists.");
		}			
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(authToken);
		ClientResponse clientResponse = client.uploadFile(fileObjectBaseURL, fileObj);
		logger.debug("Executed uploadFile for containerName: " + containerName + ", Code: " + clientResponse.getStatusCode());		
		
		if(clientResponse.getStatusCode() == 201){
			logger.debug("Executed uploadFile for containerName: " + containerName);
			return true;
		}
		
		throw new Exception("Could not upload file to container: Code: " + clientResponse.getStatusCode() + ", Reason: " + clientResponse.getEntity(String.class));
	}
	
	/**
	 * Gets the file object.
	 *
	 * @param fileName the file name
	 * @return the file object
	 * @throws Exception the exception
	 */
	public boolean getFileObject(String fileName) throws Exception {
		logger.debug("Executing getFileObject for containerName: " + containerName + ", fileName: " + fileName);
		
		//append the filename to the url
		if(!fileObjectBaseURL.endsWith("/")) {
			fileObjectBaseURL += "/";
		}
		fileObjectBaseURL += fileName;
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(authToken);
		ClientResponse clientResponse = client.executeGET(fileObjectBaseURL);
		logger.debug("Executed getFileObject for containerName: " + containerName + ", fileName: " + fileName + ",  Code: " + clientResponse.getStatusCode());

		if(clientResponse.getStatusCode() == 200) {
			return true;
		}		
		
		throw new Exception("Could not retrieve file from container: Code: " + clientResponse.getStatusCode() + ", Reason: " + clientResponse.getEntity(String.class));
	}
	
	/**
	 * Delete file object.
	 *
	 * @param fileName the file name
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean deleteFileObject(String fileName) throws Exception {
		logger.debug("Executing deleteFileObject for containerName: " + containerName + ", fileName: " + fileName);
		
		//append the filename to the url
		if(!fileObjectBaseURL.endsWith("/")) {
			fileObjectBaseURL += "/";
		}
		fileObjectBaseURL += fileName;
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(authToken);
		ClientResponse clientResponse = client.executeDELETE(fileObjectBaseURL);
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executed deleteFileObject for containerName: " + containerName + ", fileName: " + fileName);
		
		if(clientResponse.getStatusCode() == 204) {
			return true;
		}		
		
		throw new Exception("Could not delete file from container: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}		
}
