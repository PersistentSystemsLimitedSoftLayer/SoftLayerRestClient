package com.ibm.softlayer.util;

import java.util.List;

/**
 * The Class URIGenerator.
 */
public class URIGenerator {

	/** The Constant properties. */
	private static final SLProperties properties = SLProperties.getInstance();	
	
	/**
	 * Gets the url.
	 *
	 * @param accountId the account id
	 * @param api the api
	 * @return the url
	 */
	public static String getURL(String accountId, String api) {
		
		//generate the get queues URL
		StringBuffer url = new StringBuffer();
		url.append(properties.getProperty(SLProperties.SL_MESSAGING_BASE_API)).append("/");
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		
		//append the account id
		url.append(accountId).append("/");
		
		//append the auth to the URL
		url.append(api);
		
		return url.toString();
	}		
	
	/**
	 * Gets the SL base url.
	 *
	 * @param apiUrl the api url
	 * @return the SL base url
	 */
	public static String getSLBaseURL(String apiUrl) {
		StringBuffer url = new StringBuffer();
		url.append(properties.getProperty(SLProperties.SL_BASE_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		
		url.append(apiUrl);
		return url.toString();
	}	
	
	/**
	 * Gets the soft layer api url.
	 *
	 * @param pathList the path list
	 * @return the soft layer api url
	 */
	public static String getSoftLayerApiUrl(List<String> pathList) {
		StringBuffer url = new StringBuffer();
		url.append(properties.getProperty(SLProperties.SL_BASE_API));
		
		//append the path list separated by /
		if(pathList != null && pathList.size() > 0){
			for(String path : pathList) {
				if(!url.toString().endsWith("/")) {
					url.append("/");
				}
				url.append(path);
			}
		}
		
		return url.toString();
	}	
	
	/**
	 * Gets the SL messaging apiurl.
	 *
	 * @return the SL messaging apiurl
	 */
	public static String getSLMessagingAPIURL(){
		StringBuffer url = new StringBuffer();
		url.append(properties.getProperty(SLProperties.SL_MESSAGING_BASE_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		
		//append the account id
		url.append(properties.getProperty(SLProperties.SL_MESSAGING_ACCOUNTID)).append("/");
				
		url.append(APIConstants.QUEUES_API);
		return url.toString();
	}	
	
	
	/**
	 * Gets the SL messaging apiurl for topic.
	 *
	 * @return the SL messaging apiurl for topic
	 */
	public static String getSLMessagingAPIURLForTopic(){
		StringBuffer url = new StringBuffer();
		url.append(properties.getProperty(SLProperties.SL_MESSAGING_BASE_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		
		//append the account id
		url.append(properties.getProperty(SLProperties.SL_MESSAGING_ACCOUNTID)).append("/");
				
		url.append(APIConstants.TOPICS_API);
		return url.toString();
	}	
	
	
}
