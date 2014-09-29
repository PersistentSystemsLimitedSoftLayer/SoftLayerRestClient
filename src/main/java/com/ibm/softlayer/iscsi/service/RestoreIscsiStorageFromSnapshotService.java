package com.ibm.softlayer.iscsi.service;

import com.ibm.softlayer.util.APIConstants;

public class RestoreIscsiStorageFromSnapshotService extends AbstractgetIscsiNetworkStorageService {

	public RestoreIscsiStorageFromSnapshotService(String username, String apikey) {
		super(username, apikey);
		// TODO Auto-generated constructor stub
	}

	public boolean restoreIscsiStorageFromSnapshot(int iscsiVolumeId, int snapshotId) throws Exception
	{
	
		return restoreFromSnapshot(iscsiVolumeId,snapshotId,APIConstants.RESTORE_FROM_SNAPSHOT);
		
	}
	
}
