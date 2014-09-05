package com.ibm.softlayer.common.service;

import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.util.SLProperties;

/**
 * The Class SoftLayerAuthenticationService.
 */
public class AuthenticationService {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	
	/** The properties. */
	private final SLProperties properties = SLProperties.getInstance();
	
	/** The username. */
	private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/**
	 * Instantiates a new soft layer authentication service.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */
	public AuthenticationService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
	}	

	
	/**
	 * Authenticate get.
	 *
	 * @param url the url
	 * @return the client response
	 */
	public ClientResponse authenticateGET(String url)  {		
		logger.debug("Executing authenticate:GET for following URL: " + url);
		
		RestClient client = new RestClient(getClientConfig());		
		Resource resource = client.resource(url);	
		resource.header("X-Auth-User", username);
		resource.header("X-Auth-Key", apiKey);		
		
		logger.info("Calling GET API for Storage authentication: " + url);
		ClientResponse response = resource.get();		
		logger.debug("Executed authenticate:GET for following URL: " + url + ", Response Status Code: " + response.getStatusCode());
		return response;
	}
	
	/**
	 * Authenticate post.
	 *
	 * @param url the url
	 * @return the client response
	 */
	public ClientResponse authenticatePOST(String url)  {		
		logger.info("Executing authenticate:POST for following URL: " + url);
		
		RestClient client = new RestClient(getClientConfig());		
		Resource resource = client.resource(url);	
		resource.header("X-Auth-User", username);
		resource.header("X-Auth-Key", apiKey);		
		
		ClientResponse response = resource.post(null);		
		logger.info("Executed authenticate:POST for following URL: " + url + ", Response Status Code: " + response.getStatusCode());
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
