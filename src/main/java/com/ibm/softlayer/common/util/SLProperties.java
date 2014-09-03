package com.ibm.softlayer.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * The Class SLProperties.
 */
public class SLProperties {

	/** The Constant PROPS_FILE. */
	private static final String PROPS_FILE = "slclient.properties";	
	
	/** The Constant SL_PROXYHOST. */
	public static final String SL_PROXYHOST = "SL_PROXYHOST";
	
	/** The Constant SL_PROXYPORT. */
	public static final String SL_PROXYPORT = "SL_PROXYPORT";
	
	public static final String SL_BASE_API = "SL_BASE_API";
	
	public static final String SL_MESSAGING_ACCOUNTID = "SL_MESSAGING_ACCOUNTID";
	
	public static final String SL_MESSAGING_BASE_API = "SL_MESSAGING_BASE_API";
	
	/** The instance. */
	private static final SLProperties instance = new SLProperties();	
	
	/** The props. */
	private Properties props = null;
	
	/** The properties map. */
	private Map<String, String> propertiesMap = new HashMap<String, String>();
	
	/**
	 * Instantiates a new SL properties.
	 */
	private SLProperties () {
		try {							
			InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPS_FILE);
			props = new Properties();
			props.load(input);			
			
			//store all the properties in the map
			Set<Object> keys = props.keySet();
			for(Object key: keys) {
				propertiesMap.put((String) key, props.getProperty((String) key));
			}
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Gets the single instance of SLProperties.
	 *
	 * @return single instance of SLProperties
	 */
	public static SLProperties getInstance() {		
		return instance;
	}
	
	/**
	 * Gets the property.
	 *
	 * @param key the key
	 * @return the property
	 */
	public String getProperty(String key) {
		if(propertiesMap.containsKey(key)) {
			return propertiesMap.get(key);
		}
		return null;
	}	
}
