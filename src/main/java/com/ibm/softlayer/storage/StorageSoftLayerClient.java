package com.ibm.softlayer.storage;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.AbstractSoftLayerClient;
import com.ibm.softlayer.util.SoftLayerAuthenticationService;

/**
 * The Class StorageSoftLayerClient.
 */
public class StorageSoftLayerClient extends AbstractSoftLayerClient {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(StorageSoftLayerClient.class);
	
	public StorageSoftLayerClient(String token) {
		setxAuthToken(token);
		setUseAuthToken(true);
	}		
	
	/**
	 * Authenticate.
	 *
	 * @param url the url
	 * @param tenant the tenant
	 * @param username the username
	 * @param apiKey the api key
	 * @return the JSON object
	 * @throws Exception the exception
	 */
	public static JSONObject authenticate(String url, String tenant, String username, String apiKey) throws Exception {
		
		//adding the tenant to the username
		username = tenant + ":" + username;
		
		//call the service to authenticate
		SoftLayerAuthenticationService service = new SoftLayerAuthenticationService(username, apiKey);
		ClientResponse clientResponse = service.authenticateGET(url);
		String response = clientResponse.getEntity(String.class);
		
		if(clientResponse.getStatusCode() == 200){
			MultivaluedMap<String, String> headers = clientResponse.getHeaders();
			JSONObject json = new JSONObject();
			json.put(APIConstants.X_AUTH_TOKEN, headers.get(APIConstants.X_AUTH_TOKEN).get(0));
			json.put("X-Storage-Token", headers.get("X-Storage-Token").get(0));
			json.put(APIConstants.X_STORAGE_URL, headers.get(APIConstants.X_STORAGE_URL).get(0));
			logger.info("getAuthToken JSON Response: " + json);
			return json;
		}
		
		throw new Exception("Could not retrieve the token: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);
	}
	
	
}
