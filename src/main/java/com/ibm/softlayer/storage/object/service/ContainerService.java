package com.ibm.softlayer.storage.object.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.storage.StorageSoftLayerClient;

/**
 * The Class ContainerService.
 */
public class ContainerService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ContainerService.class);
	
	/** The auth token. */
	private String authToken = null;
	
	/**
	 * Instantiates a new container service.
	 *
	 * @param token the token
	 */
	public ContainerService (String token) {
		this.authToken = token;
	}
	
	/**
	 * Creates the container.
	 *
	 * @param accountURL the account url
	 * @param containerName the container name
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean createContainer(String accountURL, String containerName) throws Exception {
		logger.debug("Executing createContainer for containerName: " + containerName);
		
		StringBuffer url = new StringBuffer(accountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(containerName);
		
		StorageSoftLayerClient client = new StorageSoftLayerClient(authToken);		
		ClientResponse clientResponse = client.executePUT(url.toString());
		if(clientResponse.getStatusCode() == 201){
			logger.debug("Executed createContainer for containerName: " + containerName);
			return true;
		}
		
		throw new Exception("Could not create the container: Code: " + clientResponse.getStatusCode() + ", Reason: " + clientResponse.getEntity(String.class));
	}
	
	/**
	 * Gets the container.
	 *
	 * @param accountURL the account url
	 * @param containerName the container name
	 * @return the container
	 * @throws Exception the exception
	 */
	public JSONArray getContainer(String accountURL, String containerName) throws Exception {
		logger.debug("Executing getContainer for containerName: " + containerName);
		
		StringBuffer url = new StringBuffer(accountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(containerName);
		
		StorageSoftLayerClient client = new StorageSoftLayerClient(authToken);		
		ClientResponse clientResponse = client.executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		if(clientResponse.getStatusCode() == 200) {
			JSONArray json = new JSONArray(response);
			return json;
		} else if(clientResponse.getStatusCode() == 204) {
			return new JSONArray();
		}
		throw new Exception("Could not retrieve the container details: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}
	
	/**
	 * Gets the container meta data.
	 *
	 * @param accountURL the account url
	 * @param containerName the container name
	 * @return the container meta data
	 * @throws Exception the exception
	 */
	public JSONObject getContainerMetaData(String accountURL, String containerName) throws Exception {
		logger.debug("Executing getContainerMetaData for containerName: " + containerName);
		
		StringBuffer url = new StringBuffer(accountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(containerName);
		
		StorageSoftLayerClient client = new StorageSoftLayerClient(authToken);
		ClientResponse clientResponse = client.executeHEAD(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executed executeHEAD: URL: " + url + " clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200) {
			JSONObject json = new JSONObject(response);
			return json;
		} else if(clientResponse.getStatusCode() == 204) {
			return new JSONObject();
		}
		throw new Exception("executeHead returned following error: " + clientResponse.getStatusCode() + ", Reason: " + response);		
	}
}
