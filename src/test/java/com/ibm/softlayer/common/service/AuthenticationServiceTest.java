package com.ibm.softlayer.common.service;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import org.junit.Test;

import com.ibm.softlayer.common.service.AuthenticationService;
import com.ibm.softlayer.common.util.SLProperties;
import com.ibm.softlayer.util.UnitTestConstants;

/**
 * The Class AuthenticationServiceTest.
 */
public class AuthenticationServiceTest {	
	
	private static final SLProperties properties = SLProperties.getInstance();	
	private static final String SL_ACCOUNTID = properties.getProperty(SLProperties.SL_MESSAGING_ACCOUNTID);
	/**
	 * Test get token.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetToken() throws Exception {
		AuthenticationService service = AuthenticationService.getInstance();
		String token = service.getAuthToken(SL_ACCOUNTID, UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		assertNotNull(token);
	}
	
	/**
	 * Test get token invalid key.
	 *
	 * @throws Exception the exception
	 */
	@Test (expected = Exception.class)
	public void testGetTokenInvalidKey() throws Exception {
		String password = "a5d16217cdaabaccdb5c928a64c61afe065bd567077";		

		AuthenticationService service = AuthenticationService.getInstance();
		String token = service.getAuthToken(SL_ACCOUNTID, UnitTestConstants.SL_USERNAME, password);
		assertNull(token);
	}
}
