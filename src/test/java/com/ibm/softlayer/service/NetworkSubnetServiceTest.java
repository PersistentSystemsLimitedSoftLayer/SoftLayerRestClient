package com.ibm.softlayer.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.network.service.NetworkSubnetService;
import com.ibm.softlayer.util.UnitTestConstants;

public class NetworkSubnetServiceTest {

	@Test
	public void testGetAllVirtualServers() throws Exception {
		String ipAddress = "5.10.123.34";
		NetworkSubnetService service = new NetworkSubnetService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject json = service.getIpAddressDetails(ipAddress, null);			
		assertNotNull(json);	
		assertEquals(ipAddress, json.getString("ipAddress"));	
	}	
}
