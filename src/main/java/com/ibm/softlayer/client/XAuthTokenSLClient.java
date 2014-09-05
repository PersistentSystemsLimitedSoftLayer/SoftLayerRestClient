package com.ibm.softlayer.client;

import java.io.File;
import java.io.FileInputStream;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.apache.wink.common.internal.MultivaluedMapImpl;
import org.apache.wink.common.model.multipart.BufferedOutMultiPart;
import org.apache.wink.common.model.multipart.OutPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class XAuthTokenSLClient.
 */
public class XAuthTokenSLClient extends AbstractSoftLayerClient {	
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(XAuthTokenSLClient.class);
	
	/**
	 * Instantiates a new x auth token sl client.
	 *
	 * @param token the token
	 */
	public XAuthTokenSLClient(String token) {			
		setxAuthToken(token);
		setUseAuthToken(true);
	}
	
	/**
	 * Upload file.
	 *
	 * @param url the url
	 * @param inputFile the input file
	 * @return the client response
	 * @throws Exception the exception
	 */
	public ClientResponse uploadFile(String url, File inputFile) throws Exception {
		
		logger.info("Executing uploadFile:PUT for following URL: " + url);
		byte[] bytes = new byte[(int) inputFile.length()];
		FileInputStream fis = new FileInputStream(inputFile);
		fis.read(bytes);
		fis.close();
		
		System.out.println("upload bytes.length: " + bytes.length);
		
		//append the filename to the url
		if(!url.endsWith("/")) {
			url += "/";
		}
		url += inputFile.getName();
		
		// Create a multipart request
		BufferedOutMultiPart mp = new BufferedOutMultiPart();
		
		// Add file part
		OutPart fp = new OutPart();
		MultivaluedMap<String,String> headers = new MultivaluedMapImpl<String,String>();
		headers.add("name", "file_part");
		headers.add("filename", inputFile.getName());
		fp.setHeaders(headers);		
		
		fp.setContentType("multipart/form-data; boundary=simple boundary");
		fp.setBody(bytes);     
		mp.addPart(fp);
		
		RestClient client = new RestClient(getClientConfig());		
		Resource resource = client.resource(url);			
		resource.header("X-Auth-Token", getxAuthToken());
		
		ClientResponse clientResponse = resource.put(mp);
		String response = clientResponse.getEntity(String.class);		
		logger.info("Executed uploadFile:PUT for following URL: " + url + ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		return clientResponse;
	}
}
