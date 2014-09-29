package com.ibm.softlayer.ssl.service;

import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

public class GetAllValidExpireCertificateService {

	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GetAllValidExpireCertificateService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Get All Valid Expire Certificate Service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public GetAllValidExpireCertificateService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}	
	
	/**
	 * Get All Valid Expire Certificate Service objects.
	 * @return the jsonArray
	 * @throws Exception the exception
	 */
	public JSONArray getAllValidExpireCertificate(String certType,List<String> objectMasks) throws Exception {
		
		logger.info("Executing Get Cetification for type: " + certType);
		if(certType == null || certType.trim().length() == 0){
			throw new Exception("Cetification Type is mandatory to retrieve the Cetification Details of that type");
		}
		
		//generate the get image url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.ACCOUNT_ROOT_API));
		url.append("/");
		
		if(certType.equals("All")){
			url.append("getSecurityCertificates");
		}else if(certType.equals("Expire")){
			url.append("getExpiredSecurityCertificates");
		}else if(certType.equals("Valid")){
			url.append("getValidSecurityCertificates");
		}
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString(),objectMasks);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed Get Cetification for Type: " + certType + ", Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray jsonArr = new JSONArray(response);
			logger.debug("Get Cetification: JSON Response: " + response);
			return jsonArr;		
		}
		
		throw new Exception("Could not retrieve the get Cetification : Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}

	
	
}
