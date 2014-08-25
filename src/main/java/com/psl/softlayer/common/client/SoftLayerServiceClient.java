package com.psl.softlayer.common.client;

import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psl.softlayer.common.util.SLProperties;

/**
 * The Class SoftLayerServiceClient.
 */
public class SoftLayerServiceClient {	
	
	/** The properties. */
	private final SLProperties properties = SLProperties.getInstance();
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SoftLayerServiceClient.class);
	
	/**
	 * Execute post.
	 *
	 * @param url the url
	 * @param username the username
	 * @param password the password
	 * @return the client response
	 */
	public ClientResponse executePOST(String url, String username, String password)  {		
		logger.info("Executing POST for following URL: " + url);
		
		RestClient client = new RestClient(getClientConfig());		
		Resource resource = client.resource(url);	
		resource.header("X-Auth-User", username);
		resource.header("X-Auth-Key", password);		
		
		ClientResponse response = resource.post(null);		
		logger.info("Executed POST for following URL: " + url + ", Response Status Code: " + response.getStatusCode());
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
