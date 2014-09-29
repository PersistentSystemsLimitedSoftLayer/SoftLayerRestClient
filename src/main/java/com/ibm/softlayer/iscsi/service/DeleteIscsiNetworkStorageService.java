package com.ibm.softlayer.iscsi.service;

import com.ibm.softlayer.util.APIConstants;

public class DeleteIscsiNetworkStorageService extends AbstractgetIscsiNetworkStorageService{

	public DeleteIscsiNetworkStorageService(String username, String apikey) {
		super(username, apikey);
		// TODO Auto-generated constructor stub
	}


	public boolean deleteIscsiNetworkStorage (int iscsiVolumeId) throws Exception
	{
		 
		
		return deleteIscsi(iscsiVolumeId,APIConstants.DELETE_OBJECT_API);
		
		
	}
}
