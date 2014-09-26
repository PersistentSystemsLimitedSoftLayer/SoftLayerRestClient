package com.ibm.softlayer.ssl.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

public class GetCertificationService {

	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetCertificationService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new gets the Certification service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public GetCertificationService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}	
	
	/**
	 * Gets the Certification object.
	 *
	 * @param certId the certificate id
	 * @return the json
	 * @throws Exception the exception
	 */
	public JSONObject getCertification(String certId) throws Exception {
		
		logger.info("Executing Get Cetification: " + certId);
		if(certId == null || certId.trim().length() == 0){
			throw new Exception("Cetification Id is mandatory to retrieve the Cetification Details");
		}
		
		//generate the get image url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.SSL_API));
		url.append("/").append(certId);
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed Get Cetification: " + certId + ", Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("Get Cetification: JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not retrieve the get Cetification: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}

	
	
}
