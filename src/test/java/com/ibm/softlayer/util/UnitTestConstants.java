package com.ibm.softlayer.util;

import org.apache.log4j.PropertyConfigurator;

/**
 * The Class UnitTestConstants.
 */
public class UnitTestConstants {
	
	static {
		PropertyConfigurator.configure("log4j.properties");
	}

	/** The Constant SL_ACCOUNTID. */
	public static final String SL_ACCOUNTID = "367792";
	
	/** The Constant SL_USERNAME. */
	public static final String SL_USERNAME = "sneha_shahade";
	
	/** The Constant SL_APIKEY. */
	public static final String SL_APIKEY = "9fb255517c63d4c7ccb7eaaf3dc967e10989b759ca8dd047cf3e57ea3aa406bb";
}
