package com.ibm.softlayer.util;

import org.apache.log4j.PropertyConfigurator;

/**
 * The Class UnitTestConstants.
 */
public class UnitTestConstants {
	
	static {
		PropertyConfigurator.configure("log4j.properties");
	}

	public static final String SL_USERNAME = "runtestsuser";
	
	/** The Constant SL_APIKEY. */
	public static final String SL_APIKEY = "00d558fda0af8a6ced4ab8a77a90047529bce8d115f2515da66a30594cf5fc48";
}
