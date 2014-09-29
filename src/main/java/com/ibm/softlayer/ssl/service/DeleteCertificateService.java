package com.ibm.softlayer.ssl.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.images.service.DeleteImageService;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.URIGenerator;

public class DeleteCertificateService {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DeleteCertificateService.class);	
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	public DeleteCertificateService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;	
	}

	
	/**
	 * Delete the Certificate.
	 *
	 * @param certId the Certificate id
	 * @return the JSONObject object
	 * @throws Exception the exception
	 */
	public JSONObject deleteCertificateObject(String certId) throws Exception {
		
		//authenticate the user and retrieve the token

		
		logger.info("Executing delete Certificate for cert id: " + certId);
		if(certId == null || certId.trim().length() == 0){
			throw new Exception("Certificate Id is mandatory to delete the Certificate");
		}
		
		//generate the delete Certificate url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.SSL_API));
		url.append("/").append(certId);
				
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeDELETE(url.toString());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed delete Certificate: " + certId + ", Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200 || clientResponse.getStatusCode() == 202){
			JSONObject json = new JSONObject(response);
			logger.debug("delete Certificate: JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not delete the Certificate: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}

}
