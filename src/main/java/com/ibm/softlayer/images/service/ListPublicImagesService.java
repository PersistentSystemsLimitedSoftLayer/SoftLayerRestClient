package com.ibm.softlayer.images.service;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

public class ListPublicImagesService extends AbstractService{
	
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ListPublicImagesService.class);	
	
	
	
	public ListPublicImagesService(String username, String apikey) {
		super(username, apikey);
	}

	
	
	public JSONObject listPublicImages() throws Exception {
		
		//generate the get image url
		StringBuffer url = new StringBuffer();
		url.append(URIGenerator.getSLBaseURL(APIConstants.IMAGE_API));
		url.append("/").append("getPublicImages");
				
		SoftLayerServiceClient client = new SoftLayerServiceClient();
		ClientResponse clientResponse = client.executeGET(url.toString(), null, getCredentialsColonSeperated());
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed List Public Images:  Response Status Code: " + clientResponse.getStatusCode() + ", Message: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("List Public Images: JSON Response: " + response);
			return json;		
		}
		
		throw new Exception("Could not retrieve the List Public Images: Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	}

}
