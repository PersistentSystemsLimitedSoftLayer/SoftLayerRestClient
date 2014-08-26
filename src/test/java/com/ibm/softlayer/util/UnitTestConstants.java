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
	public static final String SL_USERNAME = "shakil_usgaonker";
	
	/** The Constant SL_APIKEY. */
	public static final String SL_APIKEY = "7545e8894920141b06d91cd3ab16bfd4eade9e996a3be6266b9fc7c4a129322b";
}
