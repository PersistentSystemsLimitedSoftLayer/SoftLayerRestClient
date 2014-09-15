package com.ibm.softlayer.api.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.sshkey.service.SshKeyService;
import com.ibm.softlayer.util.UnitTestConstants;

public class SshKeyServiceTest {

	private static final long random = System.currentTimeMillis();
	private static final String keyName = "Key-" + random;
	private static String generatedKeyId = "86348";
	
	@Test
	public void testCreateSshKey() throws Exception {
		String sshkey = "ssh-rsa AAAAB3NzaC1yc2EAAAABJQAAAQEAnKV9WV9LtxlWssgzW+a6zOLLqJBJtHPsdtoyLh7KVrRLHVkV6+Ys9SsnslpF6JbF2pclDh8Vy9F+qeqnvEMxcBQ7qXfTOTSkAI02SAgtuqnsYymQERwUEb0HPO3K6WCaFtIOEexWCwc3vG0Xk2mjxY3Knu53N+R11fBjgxeznlyq4YZgB8Wb1Wmmr5TIcT7RQyPmQBwehzYvv/mBwDR0oqLcvEMjVqsXXQpzs1ticjZ5Y+OyzcK6mzrRdz+0bC6frw/C+5KhwgnZesqdj7wpNdtDRx8fy7+rshOD0VyDXqPcbQIrhN6PJWVrVyDGaStW6iP//xfQ4zgzwLFae4n3Pw== rsa-key-20140324";
		SshKeyService service = new SshKeyService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject sshKey = service.createKey(keyName, sshkey);
		System.out.println("ticketObject: " + sshKey);
		assertNotNull(sshKey);
		assertEquals(keyName, sshKey.getString("label"));
		generatedKeyId = sshKey.getString("id");
	}
	
	@Test
	public void testGetSshKey() throws Exception {
		SshKeyService service = new SshKeyService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject sshKey = service.getSshKey(generatedKeyId);
		assertNotNull(sshKey);
		assertEquals(generatedKeyId, sshKey.getString("id"));
	}
	
	
//	private String getFileContents() {
//		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("testssh.key");
//		System.out.println(input);
//		StringBuffer key = new StringBuffer();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//		String str = null;
//		try {
//			while((str = reader.readLine()) != null){
//				key.append(str);
//				//key.append("\n");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return key.toString();
//	}
}
