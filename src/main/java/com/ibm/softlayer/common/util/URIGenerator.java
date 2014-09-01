package com.ibm.softlayer.common.util;

/**
 * The Class URIGenerator.
 */
public class URIGenerator {

	/** The Constant properties. */
	private static final SLProperties properties = SLProperties.getInstance();
	
	/**
	 * Gets the public base url.
	 *
	 * @return the public base url
	 */
	private static StringBuffer getPublicBaseURL () {
		StringBuffer url = new StringBuffer();
		url.append("https://");
		url.append(properties.getProperty(SLProperties.SL_PUBLIC_HOSTNAME)).append("/");
		url.append(properties.getProperty(SLProperties.SL_VERSION)).append("/");
		return url;
	}
	
	/**
	 * Gets the url.
	 *
	 * @param accountId the account id
	 * @param api the api
	 * @return the url
	 */
	public static String getURL(String accountId, String api) {
		
		//generate the get queues URL
		StringBuffer url = getPublicBaseURL();
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		
		//append the account id
		url.append(accountId).append("/");
		
		//append the auth to the URL
		url.append(api);
		
		return url.toString();
	}
	
	
	
	private static StringBuffer getRestPublicBaseURL () {
		StringBuffer url = new StringBuffer();
		url.append("https://");
		url.append(properties.getProperty(SLProperties.SL_BASE_API)).append("/");
		//url.append(properties.getProperty(SLProperties.SL_REST_VERSION)).append("/");
		return url;
	}
	
	/**
	 * Gets the url.
	 *
	 * @param accountId the account id
	 * @param api the api
	 * @return the url
	 */
	public static String getRestURL(String api) {
		
		//generate the get queues URL
		StringBuffer url = getRestPublicBaseURL();
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		
		//append the account id
		//url.append(accountId).append("/");
		
		//append the auth to the URL
		url.append(api);
		
		return url.toString();
	}
	
	/**
	 * Gets the SL api url.
	 *
	 * @param appendAPI the append api
	 * @return the SL api url
	 */
	public static String getVirtualGuestAPIURL(){
		StringBuffer url = new StringBuffer();
		url.append(properties.getProperty(SLProperties.SL_BASE_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(properties.getProperty(SLProperties.SL_VIRTAL_GUEST_API));
		return url.toString();
	}
	
	
	
	/**
	 * Gets the SL api url.
	 *
	 * @param appendAPI the append api
	 * @return the SL api url
	 */

	
	
	
	
	
}
