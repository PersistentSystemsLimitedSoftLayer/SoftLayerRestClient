package com.ibm.softlayer.iscsi.service;

import java.util.Arrays;
import java.util.List;

import org.apache.wink.json4j.JSONObject;

import com.ibm.softlayer.util.APIConstants;


public class CreateSnapshotOfiSCSIStorageService extends AbstractgetIscsiNetworkStorageService {

	public CreateSnapshotOfiSCSIStorageService(String username, String apikey) {
		super(username, apikey);
		// TODO Auto-generated constructor stub
	}
	

public JSONObject createSnapshotOfiSCSIStorage(int iscsiVolumeId, String notes,List<String >objectMask) throws Exception
{
	
	
	//JSONObject snapShotSpace=createSnapshotSpace(iscsiVolumeId,20);
	return createSnapshot(iscsiVolumeId, notes, APIConstants.CREATE_SNAPSHOT_ISCSI_STORAGE);
}


}
