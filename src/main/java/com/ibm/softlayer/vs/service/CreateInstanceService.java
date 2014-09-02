package com.ibm.softlayer.vs.service;

import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

/**
 * The Class CreateInstanceService.
 */
public class CreateInstanceService extends AbstractService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CreateInstanceService.class);	
	
	/**
	 * Instantiates a new creates the instance service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public CreateInstanceService(String username, String apikey) {
		super(username, apikey);
	}

	/**
	 * Creates the instance.
	 *
	 * @param hostname the hostname
	 * @return the JSON object
	 * @throws Exception the exception
	 */
	public JSONObject createInstance(String hostname) throws Exception {
		logger.info("Executing Create Instance with hostname: " + hostname);
		
		//generate the create instance url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.VIRTUAL_GUEST_ROOT_API));
		url.append("/").append("createObject");
				
		//generate the request for POST
		JSONObject requestJson = new JSONObject();
		requestJson.put("hostname", hostname);
		requestJson.put("domain", "persistent.com");
		requestJson.put("startCpus", 1);
		requestJson.put("maxMemory", 1024);
		requestJson.put("hourlyBillingFlag", true);
		requestJson.put("localDiskFlag", true);
		requestJson.put("operatingSystemReferenceCode", "UBUNTU_LATEST");
			
		JSONObject datacenter = new JSONObject();
		datacenter.put("name", "dal05");
		requestJson.put("datacenter", datacenter);			
		
		//add the vm details to the array
		JSONArray parameters = new JSONArray();
		parameters.add(requestJson);
		
		JSONObject requests = new JSONObject();
		requests.put("parameters", parameters);
		
		SoftLayerServiceClient client = new SoftLayerServiceClient();
		ClientResponse clientResponse = client.executePOST(url.toString(), requests.toString(), getCredentialsColonSeperated());
		String response = clientResponse.getEntity(String.class);
		
		logger.info("Executed Create Instance with hostnames: " + hostname + ", Status Code: " 
					+ clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 201){
			JSONObject json = new JSONObject(response);
			logger.debug("Create Instance: JSON Response: " + response);
			return json;
		}
		
		throw new Exception("Could not create Instance: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}
	
	
	/**
	 * Creates the instances.
	 *
	 * @param hostnames the hostnames
	 * @return the JSON array
	 * @throws Exception the exception
	 */
	public JSONArray createInstances(List<String> hostnames) throws Exception {
		logger.info("Executing Create Instance with hostname: " + hostnames);
		
		//generate the create instance url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.VIRTUAL_GUEST_ROOT_API));
		url.append("/").append("createObjects");
				
		//generate the request for POST
		JSONArray parameters = new JSONArray();
		for(String hostname : hostnames) {
			JSONObject requestJson = new JSONObject();
			requestJson.put("hostname", hostname);
			requestJson.put("domain", "persistent.com");
			requestJson.put("startCpus", 1);
			requestJson.put("maxMemory", 1024);
			requestJson.put("hourlyBillingFlag", true);
			requestJson.put("localDiskFlag", true);
			requestJson.put("operatingSystemReferenceCode", "UBUNTU_LATEST");
			
			JSONObject datacenter = new JSONObject();
			datacenter.put("name", "dal05");
			requestJson.put("datacenter", datacenter);			
			
			//add the vm details to the array
			parameters.add(requestJson);
		}
		
		JSONArray multipleRequests = new JSONArray();
		multipleRequests.add(parameters);
		
		JSONObject requests = new JSONObject();
		requests.put("parameters", multipleRequests);
		
		SoftLayerServiceClient client = new SoftLayerServiceClient();
		ClientResponse clientResponse = client.executePOST(url.toString(), requests.toString(), getCredentialsColonSeperated());
		String response = clientResponse.getEntity(String.class);
		
		logger.info("Executed Create Instance with hostnames: " + hostnames + ", Status Code: " 
					+ clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("Create Instance: JSON Response: " + response);
			return json;
		}
		
		throw new Exception("Could not create Instance: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);	
	}
}
