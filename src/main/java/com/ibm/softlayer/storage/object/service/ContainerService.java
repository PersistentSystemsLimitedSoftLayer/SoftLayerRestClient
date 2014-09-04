package com.ibm.softlayer.storage.object.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;

public class ContainerService {

	private static final Logger logger = LoggerFactory.getLogger(ContainerService.class);
	
	private String authToken = null;
	
	public ContainerService (String token) {
		this.authToken = token;
	}
	
	public boolean createContainer(String accountURL, String containerName) throws Exception {
		logger.debug("Executing createContainer for containerName: " + containerName);
		
		StringBuffer url = new StringBuffer(accountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(containerName);
		
		SoftLayerServiceClient client = new SoftLayerServiceClient(authToken);		
		ClientResponse clientResponse = client.executePUT(url.toString(), null);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed createContainer: " + containerName + " clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 201){
			return true;
		}
		
		throw new Exception("Could not create the container: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}
	
	public JSONObject getContainer(String accountURL, String containerName) throws Exception {
		logger.debug("Executing getContainer for containerName: " + containerName);
		
		StringBuffer url = new StringBuffer(accountURL);
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(containerName);
		
		SoftLayerServiceClient client = new SoftLayerServiceClient(authToken);		
		ClientResponse clientResponse = client.executeGET(url.toString(), null);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getContainer: " + containerName + " clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 204){
			return new JSONObject();
		}
		
		throw new Exception("Could not create the container: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}
}
