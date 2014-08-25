package com.psl.softlayer.common.service;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

/**
 * The Class AuthenticationServiceTest.
 */
public class AuthenticationServiceTest {

	static {
		PropertyConfigurator.configure("log4j.properties");
	}
	
	/**
	 * Test get token.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetToken() throws Exception {
		String accountId = "5yi4q";
		String username = "SL367792";
		String password = "a5d16217cdaabaccdb5c928a64c61afe065bd5670779da2ae37f7e1b2563ae3a";		

		AuthenticationService service = AuthenticationService.getInstance();
		String token = service.getAuthToken(accountId, username, password);
		assertNotNull(token);
	}
	
	/**
	 * Test get token invalid key.
	 *
	 * @throws Exception the exception
	 */
	@Test (expected = Exception.class)
	public void testGetTokenInvalidKey() throws Exception {
		String accountId = "5yi4q";
		String username = "SL367792";
		String password = "a5d16217cdaabaccdb5c928a64c61afe065bd567077";		

		AuthenticationService service = AuthenticationService.getInstance();
		String token = service.getAuthToken(accountId, username, password);
		assertNull(token);
	}
}
