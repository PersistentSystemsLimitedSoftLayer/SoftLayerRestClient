package com.ibm.softlayer.iscsi.service;

import org.apache.wink.json4j.JSONObject;

import com.ibm.softlayer.util.APIConstants;

public class CreateSnapshotOfiSCSIStorageService extends AbstractgetIscsiNetworkStorageService {

	public CreateSnapshotOfiSCSIStorageService(String username, String apikey) {
		super(username, apikey);
		// TODO Auto-generated constructor stub
	}
	

public JSONObject createSnapshotOfiSCSIStorage(int iscsiVolumeId, String notes) throws Exception
{
	// TODO Auto-generated method stub
	return createSnapshot(iscsiVolumeId, notes, APIConstants.CREATE_SNAPSHOT_ISCSI_STORAGE);
}


}
