package com.ibm.softlayer.common.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.util.SLProperties;

/**
 * The Class SoftLayerServiceClient.
 */
public class SoftLayerServiceClient {	
	
	/** The properties. */
	private final SLProperties properties = SLProperties.getInstance();
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SoftLayerServiceClient.class);
	
	/** The token. */
	private String token = null;	
	
	/**
	 * Instantiates a new soft layer service client.
	 *
	 * @param token the token
	 */
	public SoftLayerServiceClient(String token) {
		this.token = token;
	}
	
	/**
	 * Instantiates a new soft layer service client.
	 */
	public SoftLayerServiceClient() {
		
	}
	
	/**
	 * Execute post.
	 *
	 * @param url the url
	 * @param username the username
	 * @param password the password
	 * @return the client response
	 */
	public ClientResponse authenticate(String url, String username, String password)  {		
		logger.debug("Executing authenticate:POST for following URL: " + url);
		
		RestClient client = new RestClient(getClientConfig());		
		Resource resource = client.resource(url);	
		resource.header("X-Auth-User", username);
		resource.header("X-Auth-Key", password);		
		
		logger.info("Calling POST API: " + url);
		ClientResponse response = resource.post(null);		
		logger.debug("Executed authenticate:POST for following URL: " + url + ", Response Status Code: " + response.getStatusCode());
		return response;
	}
	
	/**
	 * Execute get.
	 *
	 * @param url the url
	 * @param requestParamsMap the request params map
	 * @return the client response
	 */
	public ClientResponse executeGET(String url, Map<String, String> requestParamsMap)  {		
		return executeGET(url, requestParamsMap, null);
	}
	
	/**
	 * Execute get.
	 *
	 * @param url the url
	 * @param requestParamsMap the request params map
	 * @param credentialsColonSeperated the credentials colon seperated
	 * @return the client response
	 */
	public ClientResponse executeGET(String url, Map<String, String> requestParamsMap, String credentialsColonSeperated)  {		
		logger.debug("Executing GET for following URL: " + url + ", requestParamsMap: " + requestParamsMap);
		
		StringBuffer requestParams = new StringBuffer();
		if(requestParamsMap != null){
			for(Map.Entry<String, String> entry : requestParamsMap.entrySet()) {				
				try {
					if(requestParams.toString().length() > 0){
						requestParams.append("&");
					}
					
					requestParams.append(entry.getKey()).append("=")
						.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage(), e);
				}
			}
			
			if(requestParams.toString().length() > 0) {
				url += "?" + requestParams.toString();
			}
			logger.info("Executing GET for following URL: " + url);
		}
		
		RestClient client = new RestClient(getClientConfig());		
		Resource resource = client.resource(url);	
		
		//credentials are used for the Virtual Guest Services
		if(credentialsColonSeperated != null && credentialsColonSeperated.trim().length() > 0) {
			String encoding = new String (Base64.encodeBase64(credentialsColonSeperated.getBytes()));
			resource.header("Authorization", "Basic "+encoding);
		} else {
			resource.header("X-Auth-Token", token);
		}	
		
		logger.info("Calling GET API: " + url);
		ClientResponse response = resource.get();		
		logger.debug("Executed GET for following URL: " + url + ", Response Status Code: " + response.getStatusCode());
		return response;
	}
	
	/**
	 * Execute put.
	 *
	 * @param url the url
	 * @param requestObject the request object
	 * @return the client response
	 */
	public ClientResponse executePUT(String url, String requestObject)  {
		return executePUT(url, requestObject, null);
	}
	
	/**
	 * Execute put.
	 *
	 * @param url the url
	 * @param requestObject the request object
	 * @param credentialsColonSeperated the credentials colon seperated
	 * @return the client response
	 */
	public ClientResponse executePUT(String url, String requestObject, String credentialsColonSeperated)  {
		logger.debug("Executing executePUT for following URL: " + url + ", requestObject: " + requestObject);
		
		RestClient client = new RestClient(getClientConfig());		
		Resource resource = client.resource(url);	
		resource.header("Content-type", "application/json");
		
		//credentials are used for the Virtual Guest Services
		if(credentialsColonSeperated != null && credentialsColonSeperated.trim().length() > 0) {
			String encoding = new String (Base64.encodeBase64(credentialsColonSeperated.getBytes()));
			resource.header("Authorization", "Basic "+encoding);
		} else {
			resource.header("X-Auth-Token", token);
		}	
		
		logger.info("Calling PUT API: " + url + ", request: " + requestObject);
		ClientResponse response = resource.put(requestObject);
		logger.debug("Executed executePUT for following URL: " + url + ", response: " + response.getStatusCode());
		return response;
	}
	
	/**
	 * Execute delete.
	 *
	 * @param url the url
	 * @return the client response
	 */
	public ClientResponse executeDELETE(String url)  {
		return executeDELETE(url, null);
	}	
	
	/**
	 * Execute delete.
	 *
	 * @param url the url
	 * @param credentialsColonSeperated the credentials colon seperated
	 * @return the client response
	 */
	public ClientResponse executeDELETE(String url, String credentialsColonSeperated)  {
		logger.debug("Executing executeDELETE for following URL: " + url);
		
		RestClient client = new RestClient(getClientConfig());		
		Resource resource = client.resource(url);	
		
		//credentials are used for the Virtual Guest Services
		if(credentialsColonSeperated != null && credentialsColonSeperated.trim().length() > 0) {
			String encoding = new String (Base64.encodeBase64(credentialsColonSeperated.getBytes()));
			resource.header("Authorization", "Basic "+encoding);
		} else {
			resource.header("X-Auth-Token", token);
		}	
		
		logger.info("Calling DELETE API: " + url);
		ClientResponse response = resource.delete();
		logger.debug("Executed executeDELETE for following URL: " + url);
		return response;
	}
	
	/**
	 * Execute post.
	 *
	 * @param url the url
	 * @param requestObject the request object
	 * @return the client response
	 */
	public ClientResponse executePOST(String url, String requestObject)  {
		return executePOST(url, requestObject, null);
	}		
	
	/**
	 * Execute post.
	 *
	 * @param url the url
	 * @param requestObject the request object
	 * @param credentialsColonSeperated the credentials colon seperated
	 * @return the client response
	 */
	public ClientResponse executePOST(String url, String requestObject, String credentialsColonSeperated)  {
		logger.debug("Executing processPOST for following URL: " + url + ", requestObject:" + requestObject);
		
		RestClient client = new RestClient(getClientConfig());		
		Resource resource = client.resource(url);	
		resource.header("Content-type", "application/json");
		
		//credentials are used for the Virtual Guest Services
		if(credentialsColonSeperated != null && credentialsColonSeperated.trim().length() > 0) {
			String encoding = new String (Base64.encodeBase64(credentialsColonSeperated.getBytes()));
			resource.header("Authorization", "Basic "+encoding);
		} else {
			resource.header("X-Auth-Token", token);
		}				
		
		logger.info("Calling POST API: " + url + ", request: " + requestObject);
		ClientResponse response = resource.post(requestObject);
		logger.debug("Executed processPOST for following URL: " + url);
		return response;
	}
	
	/**
	 * Gets the client config.
	 *
	 * @return the client config
	 */
	private ClientConfig getClientConfig() {
		ClientConfig config = new ClientConfig();
		config.proxyHost(properties.getProperty(SLProperties.SL_PROXYHOST));
		config.proxyPort(Integer.valueOf(properties.getProperty(SLProperties.SL_PROXYPORT)));
		return config;
	}
}
