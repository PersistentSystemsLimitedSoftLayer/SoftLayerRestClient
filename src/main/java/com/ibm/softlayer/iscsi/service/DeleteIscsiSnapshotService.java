package com.ibm.softlayer.iscsi.service;

import com.ibm.softlayer.util.APIConstants;

public class DeleteIscsiSnapshotService extends AbstractgetIscsiNetworkStorageService{

	public DeleteIscsiSnapshotService(String username, String apikey) {
		super(username, apikey);
		// TODO Auto-generated constructor stub
	}
	
	public boolean deleteIscsiSnapshot(int snaphotId)throws Exception
	{
		
		return deleteSnapshot(snaphotId,APIConstants.DELETE_OBJECT_API);
	}
	
}
