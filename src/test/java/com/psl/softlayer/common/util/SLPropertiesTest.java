package com.psl.softlayer.common.util;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * The Class SLPropertiesTest.
 */
public class SLPropertiesTest {

	/**
	 * Test properties file valid property.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPropertiesFileValidProperty() throws Exception {
		SLProperties instance = SLProperties.getInstance();
		String apiUserName = instance.getProperty(SLProperties.SL_USERNAME);
		assertNotNull(apiUserName);
	}
	
	/**
	 * Test properties file invalid property.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPropertiesFileInvalidProperty() throws Exception {
		SLProperties instance = SLProperties.getInstance();
		String apiUserName = instance.getProperty("Invalid.Porperty");
		assertNull(apiUserName);
	}
}
