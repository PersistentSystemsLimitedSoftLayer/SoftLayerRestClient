package com.ibm.softlayer.ssl.service;

import java.util.Map;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

public class EditCertificateService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(EditCertificateService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new edit the Certification service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public EditCertificateService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}	
	
	/**
	 * Edit the image.
	 *
	 * @param certId the certificate id
	 * @param bodyMap contain parameters to be edited
	 * @return the Boolean object
	 * @throws Exception the exception
	 */
	public Boolean editCertificateObject(String certId, Map<String,String> bodyMap) throws Exception {
		
		//authenticate the user and retrieve the token

		
		logger.info("Executing edit certificate for image id: " + certId);
		if(certId == null || certId.trim().length() == 0){
			throw new Exception("certificate Id is mandatory to edit the certificate");
		}
		
		//generate the edit certificate url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.SSL_API));
		url.append("/").append(certId).append("/editObject");
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executePOST(url.toString(),getJSON(bodyMap));
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed edit certificate: " + certId + ", Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			Boolean isedited = new Boolean(response);
			logger.debug("edit certificate: JSON Response: " + response);
			return isedited;		
		}
		
		throw new Exception("Could not edit the certificate: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}
	
	
	private String getJSON(Map<String,String> bodyMap) throws JSONException {
		JSONObject bodyelement = new JSONObject(bodyMap);
		JSONArray jarr = new JSONArray();
		jarr.add(bodyelement);
		JSONObject json = new JSONObject();
		json.put("parameters", jarr);
		System.out.println("obj json ===== "+json);
		return json.toString();
	}
	
	
	
}
