package com.ibm.softlayer.storage.object.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.XAuthTokenSLClient;

public class StaticLargeObjects {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(StaticLargeObjects.class);
	
	/** The auth token. */
	private String authToken = null;
	
	/** The storage account url. */
	private String storageAccountURL = null;
	
	/** The container name for objects. */
	private String containerName = "segments";
	
	/** The container name for manifest. */
	private String manifestContainerName = "images";
	
	/** The file object base url. */
	private String fileObjectBaseURL = null;
	
	/** the maximum size of each file "chunk" generated, in bytes */
	private long baseSize = 1024 * 1024 * 2; //2MB
	
	/**
	 * Instantiates a new file object service.
	 *
	 * @param storageURL the storage url
	 * @param token the token
	 * @param containerName the container name
	 */
	public StaticLargeObjects (String storageURL, String token) {
		this.authToken = token;
		this.storageAccountURL = storageURL;
		
		//generate the storage URL
		StringBuffer url = new StringBuffer(storageAccountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(containerName);
		fileObjectBaseURL = url.toString();
	}
	
	/**
	 * Upload Large file.
	 *
	 * @param fileLocation the file location
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean uploadLargeFile(String fileLocation) throws Exception {
		
		int subfiles = 0;
		//split file into pieces
		try {
			subfiles = split(fileLocation);
		} catch (FileNotFoundException e) {
			logger.info("File not found");
		} catch (IOException e) {
			logger.info("IO Error");
		}
		
		//create container for segments if does not exists
		try {
			ContainerService cService = new ContainerService(storageAccountURL, authToken);
			JSONArray container = cService.getContainer(containerName);
		} catch (Exception e) {
			ContainerService service = new ContainerService(storageAccountURL, authToken);
			boolean created = service.createContainer(containerName);
		}
		
		String uploadFilename = new File(fileLocation).getName();
		String subFileLocation = null;
		JSONArray manifestArray = new JSONArray();
		for(int i = 0; i<= subfiles ; i++ ) {
			subFileLocation = fileLocation + "." + i;
			
			String[] res = uploadFile(subFileLocation);

			JSONObject obj = new JSONObject();
			obj.put("path", containerName+"/"+uploadFilename+"."+i);
			obj.put("etag", res[0]);
			obj.put("size_bytes", res[1]);
			manifestArray.add(obj);
		}
		
		//create container for manifest object if does not exists
		try {
		ContainerService cService2 = new ContainerService(storageAccountURL, authToken);
		JSONArray container = cService2.getContainer(manifestContainerName);
		} catch (Exception e) {
			ContainerService cService3 = new ContainerService(storageAccountURL, authToken);
			boolean created = cService3.createContainer(manifestContainerName);
		}

		uploadManifestFile(uploadFilename, manifestArray);
		
		return true;
	}
	
	/**
	 * split the file specified by filename into pieces, each of size
	 * baseSize except for the last one, which may be smaller
	 */
	public int split(String filename) throws FileNotFoundException, IOException {
		// open the file
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
		
		
		// get the file length
		File f = new File(filename);
		long fileSize = f.length();
		
		// loop for each full chunk
		int subfile;
		for (subfile = 0; subfile < fileSize / baseSize; subfile++)
			{
			// open the output file
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filename + "." + subfile));
			
			// write the right amount of bytes
			for (int currentByte = 0; currentByte < baseSize; currentByte++)
				{
				// load one byte from the input file and write it to the output file
				out.write(in.read());
				}
				
			// close the file
			out.close();
			}
		
		// loop for the last chunk (which may be smaller than the baseSize)
		if (fileSize != baseSize * (subfile - 1))
			{
			// open the output file
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filename + "." + subfile));
			
			// write the rest of the file
			int b;
			while ((b = in.read()) != -1)
				out.write(b);
				
			// close the file
			out.close();			
			}
		
		// close the file
		in.close();
		
		logger.info("Splitting completed for file: "+filename+", with base size: "+baseSize);
		return subfile;
		}
	
	/**
	 * Upload file.
	 *
	 * @param fileLocation the file location
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public String[] uploadFile(String fileLocation) throws Exception {
		
		logger.debug("Executing uploadLargeFile for containerName: " + containerName + ", fileLocation: " + fileLocation);
		
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
			return new String[]{clientResponse.getHeaders().get("Etag").get(0), Long.toString(fileObj.length())};
		}
		
		throw new Exception("Could not upload file to container: Code: " + clientResponse.getStatusCode() + ", Reason: " + clientResponse.getEntity(String.class));
	}
		
	/**
	 * Upload manifest file.
	 *
	 * @param fileLocation the file location
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean uploadManifestFile(String manifestFileName, JSONArray manifestArray) throws Exception {
		
		logger.debug("Executing uploadManifestFile for containerName: " + manifestContainerName);		
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(authToken);
		StringBuffer url = new StringBuffer(storageAccountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(manifestContainerName);
		String manifestBaseURL = url.toString();
		ClientResponse clientResponse = client.uploadManifestFile(manifestBaseURL, manifestFileName, manifestArray);
		logger.debug("Executed uploadManifestFile");		
		
		if(clientResponse.getStatusCode() == 201){
			logger.debug("Executed uploadManifestFile");
			return true;
		}
		
		throw new Exception("Could not upload file : Code: " + clientResponse.getStatusCode() + ", Reason: " + clientResponse.getEntity(String.class));
	}

}
