package com.ibm.softlayer.vs.service;

import java.util.Arrays;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.network.service.NetworkSubnetService;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

/**
 * The Class GetInstanceService.
 */
public class GetInstanceService extends AbstractVSService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetInstanceService.class);	
	
	/**
	 * Instantiates a new gets the instance service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public GetInstanceService(String username, String apikey) {
		super(username, apikey);		
	}

	
	/**
	 * Gets the single instance of GetInstanceService.
	 *
	 * @param instanceId the instance id
	 * @return single instance of GetInstanceService
	 * @throws Exception the exception
	 */
	public JSONObject getInstance(String instanceId) throws Exception {
		logger.info("Executing Get Instance: " + instanceId);
		if(instanceId == null || instanceId.trim().length() == 0){
			throw new Exception("Instance Id is mandatory to retrieve the instance");
		}
		
		//generate the get instance url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSoftLayerApiUrl(Arrays.asList(APIConstants.VIRTUAL_GUEST_ROOT_API, instanceId, APIConstants.GETOBJECT_API)));		
		
		return get(url.toString());
	}
	
	/**
	 * Gets the instance relational info.
	 *
	 * @param instanceId the instance id
	 * @param relationalDatatype the relational datatype
	 * @return the instance relational info
	 * @throws Exception the exception
	 */
	public String getInstanceRelationalInfo(String instanceId, String relationalDatatype) throws Exception {
		if(instanceId == null || instanceId.trim().length() == 0){
			throw new Exception("Instance Id is mandatory to get instance details");
		}
		
		//generate the relational API
		String relationalAPI = "get" + String.valueOf(relationalDatatype.charAt(0)).toUpperCase() + 
				relationalDatatype.substring(1, relationalDatatype.length());
		
		String url = URIGenerator.getSoftLayerApiUrl(Arrays.asList(
				APIConstants.VIRTUAL_GUEST_ROOT_API, instanceId, relationalAPI));
		
		return getString(url.toString());
	}
	
	/**
	 * Gets the instance by ip address.
	 *
	 * @param ipAddress the ip address
	 * @return the instance by ip address
	 * @throws Exception the exception
	 */
	public JSONObject getInstanceByIPAddress(String ipAddress) throws Exception {
		logger.debug("Executing getInstanceByIPAddress, ipAddress: " + ipAddress);
		
		NetworkSubnetService service = new NetworkSubnetService(getUsername(), getApiKey());
		JSONObject ipAddressDetails = service.getIpAddressDetails(ipAddress, Arrays.asList("id"));
		String ipAddressId = ipAddressDetails.getString("id");

		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSoftLayerApiUrl(Arrays.asList(APIConstants.SL_NETWORK_SUBNET_IPADDRESS_ROOT_API, ipAddressId, APIConstants.GET_VIRTUAL_GUEST_API)));		
		
		JSONObject instance = get(url.toString());
		logger.debug("Executing getInstanceByIPAddress, ipAddress: " + ipAddress + ", instance:" + instance);
		return instance;
	}
	
	/**
	 * Gets the instance by key.
	 *
	 * @param attributeKey the attribute key
	 * @param attributeValue the attribute value
	 * @return the instance by key
	 * @throws Exception the exception
	 */
	public JSONArray getInstanceByKey(String attributeKey, String attributeValue) throws Exception {	
		
		logger.debug("Executing getInstanceByKey, key: " + attributeKey + ", Value: " + attributeValue);
		String url = URIGenerator.getSoftLayerApiUrl(Arrays.asList(
				APIConstants.ACCOUNT_ROOT_API, APIConstants.GET_VIRTUAL_GUESTS_API));
		
		String objectFilter = null;
		if((attributeKey != null && attributeKey.trim().length() > 0) && (attributeValue !=null && attributeValue.length() > 0)){
			JSONObject operation = new JSONObject();
			operation.put("operation", attributeValue);
			
			JSONObject requestJson = new JSONObject();
			requestJson.put(attributeKey, operation);
			
			JSONObject filter = new JSONObject();
			filter.put("virtualGuests", requestJson);			
			
			objectFilter = filter.toString();
		}
		
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(getUsername(), getApiKey());
		ClientResponse clientResponse = client.executeGETWithObjectFilter(url, objectFilter);
		String response = clientResponse.getEntity(String.class);
		logger.debug("Executed getInstanceByKey, key: " + attributeKey + ", Value: " + attributeValue + ", Status Code: " + clientResponse.getStatusCode());
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);			
			return json;		
		}
		
		throw new Exception("Could not retrieve getInstanceByKey, Code: " + clientResponse.getStatusCode() + ", Reason: " + response);		
	}
}
