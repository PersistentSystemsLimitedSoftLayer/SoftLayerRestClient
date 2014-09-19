package com.ibm.softlayer.api.service;

import static junit.framework.Assert.assertNotNull;

import java.util.Arrays;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.iscsi.service.CreateSnapshotOfiSCSIStorageService;
import com.ibm.softlayer.iscsi.service.GetIscsiNetworkStorageService;
import com.ibm.softlayer.iscsi.service.GetOneIscsiNetworkStorageService;
import com.ibm.softlayer.util.UnitTestConstants;

public class IscsiService {
	private static int iscsiVolumeId=0;

	@Test
	public void testGetIscsiNetworkStorage() throws Exception {
		GetIscsiNetworkStorageService service= new GetIscsiNetworkStorageService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray iscsiNetworkArray = service.getAllIscsiNetworkStorage();
		assertNotNull(iscsiNetworkArray);
		JSONObject iscsiNetworkObject1= iscsiNetworkArray.getJSONObject(0);
		iscsiVolumeId=iscsiNetworkObject1.getInt("id");
	}
	@Test
	public void testGetIscsiNetworkStorageobjectMask() throws Exception {
		GetIscsiNetworkStorageService service= new GetIscsiNetworkStorageService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray iscsiNetworkArray = service.getAllIscsiNetworkStorage(Arrays.asList("id","capacityGb","username"));
		assertNotNull(iscsiNetworkArray);
	}
	@Test
	public void testGetDetailIscsiNetworkStorage() throws Exception {
		GetOneIscsiNetworkStorageService service= new GetOneIscsiNetworkStorageService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject iscsiNetworkObject = service.getOneIscsiNetworkStorage(iscsiVolumeId);
		assertNotNull(iscsiNetworkObject);
	}
	
}
