package com.ibm.softlayer.sshkey.service;

import java.util.Arrays;
import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class SshKeyService.
 */
public class SshKeyService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SshKeyService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new ssh key service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public SshKeyService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
	}
	
	/**
	 * Creates the key.
	 *
	 * @param keyName the key name
	 * @param sshkey the sshkey
	 * @return the JSON object
	 * @throws Exception the exception
	 */
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
	
	/**
	 * Gets the ssh key.
	 *
	 * @param keyId the key id
	 * @param objectMasks the object masks
	 * @return the ssh key
	 * @throws Exception the exception
	 */
	public JSONObject getSshKey(String keyId, List<String> objectMasks) throws Exception {
		logger.debug("Executing getSshKey with keyId: " + keyId);
		
		// get the URL for Get sshKey by Id
		String url = URIGenerator.getSoftLayerApiUrl(Arrays.asList(
				APIConstants.SOFTLAYER_SECURITY_SSH_KEY, keyId, APIConstants.GETOBJECT_API));
		
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url, objectMasks);
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executed getSshKey with keyId: " + keyId + ", Response Status Code: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("Executed getSshKey with keyId: " + keyId + ": JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Error: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}
	
	/**
	 * Gets the ssh keys.
	 *
	 * @param filterKey the filter key
	 * @param filterValue the filter value
	 * @param objectMasks the object masks
	 * @return the ssh keys
	 * @throws Exception the exception
	 */
	public JSONArray getSshKeys(String filterKey, String filterValue, List<String> objectMasks) throws Exception {
		logger.debug("Executing getSshKeys; filter: keyName: " + filterKey + ", Value: " + filterValue + ", Masks: " + objectMasks);
		
		String url = URIGenerator.getSoftLayerApiUrl(Arrays.asList(APIConstants.ACCOUNT_ROOT_API, "getSshKeys"));
		
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url, "sshKeys", filterKey, filterValue, objectMasks);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed List Public Images:  Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("Executing getSshKeys; filter: keyName: " + filterKey + ", JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not retrieve the getSshKeys: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);				
	}
	
	/**
	 * Delete ssh key.
	 *
	 * @param keyId the key id
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean deleteSshKey(String keyId) throws Exception {
		logger.debug("Executing deleteSshKey with keyId: " + keyId);
		
		// get the URL for Get sshKey by Id
		String url = URIGenerator.getSoftLayerApiUrl(Arrays.asList(
				APIConstants.SOFTLAYER_SECURITY_SSH_KEY, keyId, APIConstants.DELETE_OBJECT_API));
		
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeDELETE(url);
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executed deleteSshKey with keyId: " + keyId + ", Response Status Code: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			return Boolean.valueOf(response);	
		}
		
		throw new Exception("Error: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}
}
