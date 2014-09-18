package com.ibm.softlayer.iscsi.service;

import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.SLAPIUtil;
import com.ibm.softlayer.util.URIGenerator;

public abstract class AbstractgetIscsiNetworkStorageService {
	
private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AbstractgetIscsiNetworkStorageService.class);
	
	/**
	 * Instantiates a new abstract get iSCSI network Storage details.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */

	public AbstractgetIscsiNetworkStorageService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
	}
	
 public JSONArray getIscsiNetworkStorage(String iscsiUrl,List<String> objectMasks) throws Exception
 {
	 logger.info("Executing getIscsiNetworkStorage: " + iscsiUrl + " for username: " + username);
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.ACCOUNT_ROOT_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(iscsiUrl);
		
		SLAPIUtil.processObjectMasks(url, objectMasks);
		
		
		//execute the get iSCSI Network Storage call
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed get iSCSI Network Storage call for username: " + username + ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("getIscsiNetworkStorage: " + iscsiUrl + " JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve getIscsiNetworkStorage: " + iscsiUrl + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	 
	 }
 
 
 public JSONObject getIscsiById(int iscsiID , String iscsiUrl,List<String> objectMasks) throws Exception
 {
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.ISCSI_NETWORK_STORAGE_ROOT_URL));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(iscsiID).append("/").append(iscsiUrl);
		
		SLAPIUtil.processObjectMasks(url, objectMasks);
		
		
		//execute the get get Iscsi Network Storage call
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed  get Iscsi Network Storage By ID "+iscsiID+" for username: " + username + ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("getIscsiNetworkStorage By ID: " + iscsiUrl + " JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve getIscsiNetworkStorage: " + iscsiUrl + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	 
 }
 
 
 public JSONObject createSnapshot(int iscsiVloumeId, String notes ,String iscsiUrl) throws Exception
 {
	 StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.ISCSI_NETWORK_STORAGE_ROOT_URL));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(iscsiVloumeId).append("/").append(iscsiUrl);

		
		
		//execute POST call to craete snapshot of Iscsi Network Storage 
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		
		//ClientResponse clientResponse = client.executeGET(url.toString());
		JSONObject requestObject= new JSONObject();
		requestObject.put("notes", notes);
		
		//ClientResponse clientResponse =client.executePOST(url.toString(),null);
		ClientResponse clientResponse1 =client.executePOST(url.toString(), requestObject.toString());
		
		String response = clientResponse1.getEntity(String.class);
		logger.info("Executed  Create Snapshot of  Iscsi Network Storage of Volume "+iscsiVloumeId+" for username: " + username + ", clientResponse: " + clientResponse1.getStatusCode() + ", response: " + response);
		
		if(clientResponse1.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("getIscsiNetworkStorage By ID: " + iscsiUrl + " JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not Create snapshot of  iSCSI Network Storage: " + iscsiUrl + ": Code: " + clientResponse1.getStatusCode() + ", Reason: " + response);			
	 
 }
}


