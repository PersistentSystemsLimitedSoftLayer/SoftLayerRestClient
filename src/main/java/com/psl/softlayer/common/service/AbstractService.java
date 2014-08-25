package com.psl.softlayer.common.service;

import com.psl.softlayer.common.util.SLProperties;

/**
 * The Class AbstractService.
 */
public abstract class AbstractService {

	/** The properties. */
	private final SLProperties properties = SLProperties.getInstance();
	
	/**
	 * Gets the public base url.
	 *
	 * @return the public base url
	 */
	public StringBuffer getPublicBaseURL () {
		StringBuffer url = new StringBuffer();
		url.append("https://");
		url.append(properties.getProperty(SLProperties.SL_PUBLIC_HOSTNAME)).append("/");
		url.append(properties.getProperty(SLProperties.SL_VERSION)).append("/");
		return url;
	}
}
