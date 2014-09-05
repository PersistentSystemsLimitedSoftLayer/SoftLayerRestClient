package com.ibm.softlayer.storage.object.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.common.internal.providers.multipart.MultiPartParser;
import org.apache.wink.common.model.multipart.InMultiPart;
import org.apache.wink.common.model.multipart.InPart;
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
	public byte[] getFileObject(String fileName) throws Exception {
		logger.debug("Executing getFileObject for containerName: " + containerName + ", fileName: " + fileName);
		
		//append the filename to the url
		if(!fileObjectBaseURL.endsWith("/")) {
			fileObjectBaseURL += "/";
		}
		fileObjectBaseURL += fileName;
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(authToken);
		ClientResponse clientResponse = client.executeGET(fileObjectBaseURL);
		logger.debug("Executed getFileObject for containerName: " + containerName + ", fileName: " + fileName + ",  Code: " + clientResponse.getStatusCode());

		byte[] bytes = null;
		if(clientResponse.getStatusCode() == 200) {
			String response = clientResponse.getEntity(String.class);
			InputStream stream = new ByteArrayInputStream(response.getBytes(Charset.defaultCharset()));
			
			//split the response into Parts
			MultiPartParser parser = new MultiPartParser(stream, "simple boundary");
            InMultiPart ins = new InMultiPart(parser);
            InPart part = ins.next();
            InputStream input = part.getInputStream();
            
            //convert the input stream into byte[]
            int nRead;
            byte[] data = new byte[16384];
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            while ((nRead = input.read(data, 0, data.length)) != -1) {
              buffer.write(data, 0, nRead);
            }
            buffer.flush();
            bytes = buffer.toByteArray();           			
			return bytes;
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
