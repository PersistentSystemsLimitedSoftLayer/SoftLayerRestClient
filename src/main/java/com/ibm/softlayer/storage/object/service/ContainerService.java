package com.ibm.softlayer.storage.object.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.XAuthTokenSLClient;

/**
 * The Class ContainerService.
 */
public class ContainerService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ContainerService.class);
	
	/** The auth token. */
	private String authToken = null;
	
	/** The storage account url. */
	private String storageAccountURL = null;
	
	/**
	 * Instantiates a new container service.
	 *
	 * @param storageURL the storage url
	 * @param token the token
	 */
	public ContainerService (String storageURL, String token) {
		this.authToken = token;
		this.storageAccountURL = storageURL;
	}
	
	
	/**
	 * Creates the container.
	 *
	 * @param containerName the container name
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean createContainer(String containerName) throws Exception {
		logger.debug("Executing createContainer for containerName: " + containerName);
		
		StringBuffer url = new StringBuffer(storageAccountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(containerName);
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(authToken);		
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
	 * @param containerName the container name
	 * @return the container
	 * @throws Exception the exception
	 */
	public JSONArray getContainer(String containerName) throws Exception {
		logger.debug("Executing getContainer for containerName: " + containerName);
		
		StringBuffer url = new StringBuffer(storageAccountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(containerName);
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(authToken);		
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
	 * Delete container.
	 *
	 * @param containerName the container name
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean deleteContainer(String containerName) throws Exception {
		logger.debug("Executing deleteContainer for containerName: " + containerName);
		
		StringBuffer url = new StringBuffer(storageAccountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(containerName);
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(authToken);		
		ClientResponse clientResponse = client.executeDELETE(url.toString());
		String response = clientResponse.getEntity(String.class);
		if(clientResponse.getStatusCode() == 204) {
			return true;
		}
		throw new Exception("Could not delete the container: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}
	
	/**
	 * Adds the container meta data.
	 *
	 * @param containerName the container name
	 * @param metaData the meta data
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean addContainerMetaData(String containerName, Map<String, String> metaData) throws Exception {
		logger.debug("Executing addContainerMetaData for containerName: " + containerName + ", metaData: " + metaData);
		
		StringBuffer url = new StringBuffer(storageAccountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(containerName);
		
		// add "X-Container-Meta-" to the key names
		Map<String, String> updatedMetaData = new HashMap<String, String>();
		if(metaData != null && metaData.size() > 0){
			Iterator<Map.Entry<String, String>> it = metaData.entrySet().iterator();			
			while(it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				updatedMetaData.put("X-Container-Meta-" + entry.getKey(), entry.getValue());				
			}
		}
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(authToken);
		ClientResponse clientResponse = client.executePOST(url.toString(), null, updatedMetaData);
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executed executeHEAD: URL: " + url + " clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 204) {
			return true;
		}
		throw new Exception("addContainerMetaData returned following error: " + clientResponse.getStatusCode() + ", Reason: " + response);		
	}
	
	/**
	 * Gets the container meta data.
	 *
	 * @param containerName the container name
	 * @return the container meta data
	 * @throws Exception the exception
	 */
	public JSONObject getContainerMetaData(String containerName) throws Exception {
		logger.debug("Executing getContainerMetaData for containerName: " + containerName);
		
		StringBuffer url = new StringBuffer(storageAccountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(containerName);
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(authToken);
		ClientResponse clientResponse = client.executeHEAD(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executed executeHEAD: URL: " + url + " clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 204) {
			MultivaluedMap<String, String> headers = clientResponse.getHeaders();
			JSONObject json = new JSONObject();
			if(headers != null && headers.size() > 0){
				for(Map.Entry<String, List<String>> entry : headers.entrySet()) {	
					if(entry.getKey() != null && entry.getKey().startsWith("X-")) {
						json.put(entry.getKey(), entry.getValue().get(0));
					}
				}
			}
			return json;
		}
		throw new Exception("getContainerMetaData returned following error: " + clientResponse.getStatusCode() + ", Reason: " + response);		
	}
	
	/**
	 * Removes the container meta data.
	 *
	 * @param containerName the container name
	 * @param metaData the meta data
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean removeContainerMetaData(String containerName, Map<String, String> metaData) throws Exception {
		logger.debug("Executing addContainerMetaData for containerName: " + containerName + ", metaData: " + metaData);
		
		StringBuffer url = new StringBuffer(storageAccountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(containerName);
		
		// add "X-Container-Meta-" to the key names
		Map<String, String> updatedMetaData = new HashMap<String, String>();
		if(metaData != null && metaData.size() > 0){
			Iterator<Map.Entry<String, String>> it = metaData.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				if(entry.getValue().trim().length() == 0 || entry.getValue().trim().equals("x")) {
					updatedMetaData.put("X-Remove-Container-Meta-" + entry.getKey(), "x");
				} else {
					updatedMetaData.put("X-Container-Meta-" + entry.getKey(), entry.getValue());
				}				
			}			
		}
		
		XAuthTokenSLClient client = new XAuthTokenSLClient(authToken);
		ClientResponse clientResponse = client.executePOST(url.toString(), null, updatedMetaData);
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executed executeHEAD: URL: " + url + " clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 204) {
			return true;
		}
		throw new Exception("addContainerMetaData returned following error: " + clientResponse.getStatusCode() + ", Reason: " + response);		
	}
}
