package com.ibm.softlayer.api.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.Arrays;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.softlayer.sshkey.service.SshKeyService;
import com.ibm.softlayer.util.UnitTestConstants;

public class SshKeyServiceTest {

	private static final long random = 1410769224291l; //System.currentTimeMillis();
	private static String keyName = null;
	private static String generatedKeyId = "86348";
	
	@BeforeClass
	public static void setUp() {
		keyName = "Key-" + random;
	}
	
	@Test
	public void testCreateSshKey() throws Exception {
		String sshkey = "ssh-rsa AAAAB3NzaC1yc2EAAAABJQAAAQEAnKV9WV9LtxlWssgzW+a6zOLLqJBJtHPsdtoyLh7KVrRLHVkV6+Ys9SsnslpF6JbF2pclDh8Vy9F+qeqnvEMxcBQ7qXfTOTSkAI02SAgtuqnsYymQERwUEb0HPO3K6WCaFtIOEexWCwc3vG0Xk2mjxY3Knu53N+R11fBjgxeznlyq4YZgB8Wb1Wmmr5TIcT7RQyPmQBwehzYvv/mBwDR0oqLcvEMjVqsXXQpzs1ticjZ5Y+OyzcK6mzrRdz+0bC6frw/C+5KhwgnZesqdj7wpNdtDRx8fy7+rshOD0VyDXqPcbQIrhN6PJWVrVyDGaStW6iP//xfQ4zgzwLFae4n3Pw== rsa-key-20140324";
		SshKeyService service = new SshKeyService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject sshKey = service.createKey(keyName, sshkey);
		assertNotNull(sshKey);
		assertEquals(keyName, sshKey.getString("label"));
		generatedKeyId = sshKey.getString("id");
	}
	
	@Test
	public void testGetSshKey() throws Exception {
		SshKeyService service = new SshKeyService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject sshKey = service.getSshKey(generatedKeyId, null);
		assertNotNull(sshKey);
		assertEquals(generatedKeyId, sshKey.getString("id"));
	}
	
	@Test
	public void testGetSshKeyWithObjectMask() throws Exception {
		SshKeyService service = new SshKeyService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject sshKey = service.getSshKey(generatedKeyId, Arrays.asList("id"));
		assertNotNull(sshKey);
		assertEquals(1, sshKey.size());
		assertEquals(generatedKeyId, sshKey.getString("id"));
	}
	
	@Test
	public void testGetSshKeys() throws Exception {
		SshKeyService service = new SshKeyService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray sshKeys = service.getSshKeys(null, null, null);
		assertNotNull(sshKeys);
	}
	
	@Test
	public void testGetSshKeysWithFilter() throws Exception {
		SshKeyService service = new SshKeyService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray sshKeys = service.getSshKeys("label", keyName, null);
		assertNotNull(sshKeys);
		assertEquals(1, sshKeys.size());
		assertEquals(keyName, sshKeys.getJSONObject(0).getString("label"));
	}
	
	@Test
	public void testGetSshKeysWithFilterAndMask() throws Exception {
		SshKeyService service = new SshKeyService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray sshKeys = service.getSshKeys("label", keyName, Arrays.asList("id", "label", "key"));
		assertNotNull(sshKeys);
		assertEquals(1, sshKeys.size());
		assertEquals(3, sshKeys.getJSONObject(0).size());
		assertEquals(keyName, sshKeys.getJSONObject(0).getString("label"));
	}
	
	@Test
	public void testGetSshKeysWithInvalidFilterKey() throws Exception {
		SshKeyService service = new SshKeyService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray sshKeys = service.getSshKeys("label", "Key-122", null);
		assertNotNull(sshKeys);
		assertEquals(0, sshKeys.size());
	}
	
	@Test
	public void testDeleteSshKey() throws Exception {
		SshKeyService service = new SshKeyService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		boolean deleted = service.deleteSshKey(generatedKeyId);
		assertNotNull(deleted);
		assertEquals(true, deleted);
	}
}
