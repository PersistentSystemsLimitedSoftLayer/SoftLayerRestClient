package com.ibm.softlayer.sshkey.service;

import java.util.Arrays;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

public class SshKeyService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SshKeyService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	public SshKeyService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
	}
	
	public JSONObject createKey(String keyName, String sshkey) throws Exception {
		logger.debug("Creating sshKey with keyname: " + keyName);
		
		String url = URIGenerator.getSoftLayerApiUrl(Arrays.asList(
				APIConstants.SOFTLAYER_SECURITY_SSH_KEY, APIConstants.CREATEOBJECT_API));				
        
		JSONObject requestJson = new JSONObject();
		requestJson.put("key", sshkey);
		requestJson.put("label", keyName);
		requestJson.put("notes", "This is test ssh key");			
		
		//add the vm details to the array
		JSONArray parameters = new JSONArray();
		parameters.add(requestJson);
		
		JSONObject requests = new JSONObject();
		requests.put("parameters", parameters);
		
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executePOST(url.toString(), requests.toString());
		String response = clientResponse.getEntity(String.class);		
		logger.info("Executed Create sshKey with sshKey: " + keyName + ", Status Code: " 
					+ clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 201){
			JSONObject json = new JSONObject(response);
			logger.debug("Create Instance: JSON Response: " + response);
			return json;
		}
		
		throw new Exception("Could not create sshkey: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}
	
	public JSONObject getSshKey(String keyId) throws Exception {
		logger.debug("Executing getSshKey with keyId: " + keyId);
		
		// get the URL for Get sshKey by Id
		String url = URIGenerator.getSoftLayerApiUrl(Arrays.asList(
				APIConstants.SOFTLAYER_SECURITY_SSH_KEY, keyId, APIConstants.GETOBJECT_API));
		
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url, null);
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executed getSshKey with keyId: " + keyId + ", Response Status Code: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("Executed getSshKey with keyId: " + keyId + ": JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Error: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}
}
