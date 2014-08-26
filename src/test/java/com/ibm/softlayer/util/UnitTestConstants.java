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
	public static final String SL_ACCOUNTID = "5yi4q";
	
	/** The Constant SL_USERNAME. */
	public static final String SL_USERNAME = "runtestsuser";
	
	/** The Constant SL_APIKEY. */
	public static final String SL_APIKEY = ""; // please set this before execution
}
