package com.ibm.softlayer.common.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

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
	
	/**
	 * Execute post.
	 *
	 * @param url the url
	 * @param username the username
	 * @param password the password
	 * @return the client response
	 */
	public ClientResponse authenticate(String url, String username, String password)  {		
		logger.info("Executing authenticate:POST for following URL: " + url);
		
		RestClient client = new RestClient(getClientConfig());		
		Resource resource = client.resource(url);	
		resource.header("X-Auth-User", username);
		resource.header("X-Auth-Key", password);		
		
		ClientResponse response = resource.post(null);		
		logger.info("Executed authenticate:POST for following URL: " + url + ", Response Status Code: " + response.getStatusCode());
		return response;
	}
	
	/**
	 * Execute get.
	 *
	 * @param url the url
	 * @param token the token
	 * @param requestParamsMap the request params map
	 * @return the client response
	 */
	public ClientResponse executeGET(String url, String token, Map<String, String> requestParamsMap)  {		
		logger.info("Executing GET for following URL: " + url + ", requestParamsMap: " + requestParamsMap);
		
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
		resource.header("X-Auth-Token", token);
		
		ClientResponse response = resource.get();		
		logger.info("Executed GET for following URL: " + url + ", Response Status Code: " + response.getStatusCode());
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
