package com.ibm.softlayer.network.service;

import java.util.Arrays;
import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.SLAPIUtil;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class NetworkSubnetService.
 */
public class NetworkSubnetService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(NetworkSubnetService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;	
	
	/**
	 * Instantiates a new network subnet service.
	 *
	 * @param username the username
	 * @param apiKey the api key
	 */
	public NetworkSubnetService(String username, String apiKey) {
		this.username = username;
		this.apiKey = apiKey;
	}	

	/**
	 * Gets the ip address details.
	 *
	 * @param ipAddress the ip address
	 * @param objectMasks the object masks
	 * @return the ip address details
	 * @throws Exception the exception
	 */
	public JSONObject getIpAddressDetails(String ipAddress, List<String> objectMasks) throws Exception {
		logger.info("Executing getIpAddressDetails for ipAddress: " + ipAddress + ", objectMasks: " + objectMasks);
		
		StringBuffer url = new StringBuffer(URIGenerator.getSoftLayerApiUrl(Arrays.asList(
				APIConstants.SL_NETWORK_SUBNET_IPADDRESS_ROOT_API, APIConstants.GET_BYIPADDRESSES_API, ipAddress)));		
		SLAPIUtil.processObjectMasks(url, objectMasks);
		
		//execute the GET Call
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);	
		ClientResponse clientResponse = client.executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed getIpAddressDetails for ipAddress: " + ipAddress + ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("getIpAddressDetails for ipAddress: " + ipAddress + " JSON Response: " + response);
			return json;
		}
		
		throw new Exception("Could not retrieve getIpAddressDetails for ipAddress: " + ipAddress + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}		
}
