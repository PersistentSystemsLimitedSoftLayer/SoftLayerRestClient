package com.ibm.softlayer.iscsi.service;

import com.ibm.softlayer.util.APIConstants;

public class CancelIscsiNetworkStorageService extends AbstractgetIscsiNetworkStorageService {

	public CancelIscsiNetworkStorageService(String username, String apikey) {
		super(username, apikey);
		// TODO Auto-generated constructor stub
	}

	public boolean cancelIscsiNetworkStorage(int iscsiVolumeId) throws Exception
	{
		return cancelIscsi(iscsiVolumeId,APIConstants.CANCEL_ITEM);
	}
}
