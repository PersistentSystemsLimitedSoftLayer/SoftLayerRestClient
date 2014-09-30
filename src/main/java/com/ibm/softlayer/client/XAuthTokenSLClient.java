package com.ibm.softlayer.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.apache.wink.json4j.JSONArray;
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
		InputStream fis = new FileInputStream(inputFile);
		fis.read(bytes);
		fis.close();
		
		//System.out.println("upload bytes.length: " + bytes.length);
		
		//append the filename to the url
		if(!url.endsWith("/")) {
			url += "/";
		}
		url += inputFile.getName();
		
		RestClient client = new RestClient(getClientConfig());		
		Resource resource = client.resource(url);			
		resource.header("X-Auth-Token", getxAuthToken());
		resource.header("Content-Type", "*/*");
		
		ClientResponse clientResponse = resource.put(new ByteArrayInputStream(bytes));
		String response = clientResponse.getEntity(String.class);		
		logger.info("Executed uploadFile:PUT for following URL: " + url + ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		fis.close();
		return clientResponse;
	}
	
	/**
	 * Upload manifest file.
	 *
	 * @param url the url
	 * @param inputFile the input file
	 * @return the client response
	 * @throws Exception the exception
	 */
	public ClientResponse uploadManifestFile(String url, String manifestFileName, JSONArray manifestArray) throws Exception {
		
		logger.info("Executing uploadManifestFile:PUT for following URL: " + url);
		String queryParameter = "?multipart-manifest=put";
		
		//append the filename and query parameter to the url
		if(!url.endsWith("/")) {
			url += "/";
		}
		url += manifestFileName;		
		url += queryParameter;		
		
		RestClient client = new RestClient(getClientConfig());		
		Resource resource = client.resource(url);			
		resource.header("X-Auth-Token", getxAuthToken());
		String manifestArrayString = manifestArray.toString();
		ClientResponse clientResponse = resource.put(manifestArrayString);
		String response = clientResponse.getEntity(String.class);		
		logger.info("Executed uploadManifestFile:PUT for following URL: " + url + ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		return clientResponse;
	}
	
	/**
	 * Download file.
	 *
	 * @param url the url
	 * @return the client response
	 * @throws Exception the exception
	 */
	public ClientResponse downloadFile(String url) throws Exception {
		logger.info("Executing downloadFile:GET for following URL: " + url);
		RestClient client = new RestClient(getClientConfig());		
		Resource resource = client.resource(url);		
		resource.header("X-Auth-Token", getxAuthToken());
		
		ClientResponse clientResponse = resource.accept(MediaType.APPLICATION_ATOM_XML).get();
		logger.info("Executed downloadFile:GET for following URL: " + url + ", clientResponse: " + clientResponse.getStatusCode());
		return clientResponse;
	}
}
