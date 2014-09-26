package com.ibm.softlayer.api.service;

import static junit.framework.Assert.assertNotNull;

import java.util.Arrays;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.iscsi.service.CreateIscsiNetworkStorageService;
import com.ibm.softlayer.iscsi.service.CreateSnapshotOfiSCSIStorageService;
import com.ibm.softlayer.iscsi.service.DeleteIscsiNetworkStorageService;
import com.ibm.softlayer.iscsi.service.DeleteIscsiSnapshotService;
import com.ibm.softlayer.iscsi.service.GetIscsiNetworkStorageService;
import com.ibm.softlayer.iscsi.service.GetOneIscsiNetworkStorageService;
import com.ibm.softlayer.iscsi.service.RestoreIscsiStorageFromSnapshotService;
import com.ibm.softlayer.util.UnitTestConstants;

public class IscsiService {
	private static int iscsiVolumeId=3808800;
	private static int snaphotId=3819960;
	

	@Test
	public void testGetIscsiNetworkStorage() throws Exception {
		GetIscsiNetworkStorageService service= new GetIscsiNetworkStorageService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray iscsiNetworkArray = service.getAllIscsiNetworkStorage();
		assertNotNull(iscsiNetworkArray);
		JSONObject iscsiNetworkObject1= iscsiNetworkArray.getJSONObject(1);
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
	
/*	
	@Test
		public void testCreateIscsiNetworkStorage() throws Exception {
		CreateIscsiNetworkStorageService service = new CreateIscsiNetworkStorageService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject iscsiNetworkObject = service.createIscsi();
		assertNotNull(iscsiNetworkObject);
	}
*/
	
/*	@Test
	public void testCreateSnapshotOfIscsiNetworkStorage() throws Exception {
		CreateSnapshotOfiSCSIStorageService service= new CreateSnapshotOfiSCSIStorageService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject iscsiNetworkObject = service.createSnapshotOfiSCSIStorage(iscsiVolumeId, "this is for test", null);
		assertNotNull(iscsiNetworkObject);
		snaphotId=iscsiNetworkObject.getInt("id");

	}	
*/
	
/*
	
	@Test
	public void testRestoreIscsiStorageFromSnapshot() throws Exception{
		RestoreIscsiStorageFromSnapshotService service=new RestoreIscsiStorageFromSnapshotService(UnitTestConstants.SL_USERNAME,UnitTestConstants.SL_APIKEY);
		boolean result= service.restoreIscsiStorageFromSnapshot(iscsiVolumeId, snaphotId);
		assertNotNull(result);
	}
*/

/*	
//	@Test void testDeleteIscsiSnapshot() throws Exception{
//		DeleteIscsiSnapshotService service =new DeleteIscsiSnapshotService(UnitTestConstants.SL_USERNAME,UnitTestConstants.SL_APIKEY);
//		boolean result= service.deleteIscsiSnapshot(snaphotId);
//		assertNotNull(result);
//	
//	}
*/
		

/*	
  	@Test
	public void testDeleteIscsiNetworkStorage() throws Exception{
		DeleteIscsiNetworkStorageService  service =new DeleteIscsiNetworkStorageService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		boolean iscsiNetworkObject = service.deleteIscsiNetworkStorage(snaphotId);
		assertNotNull(iscsiNetworkObject);
	
	}		
*/	

	
	
}


