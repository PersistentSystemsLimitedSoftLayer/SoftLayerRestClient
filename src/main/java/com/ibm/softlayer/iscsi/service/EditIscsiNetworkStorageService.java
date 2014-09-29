package com.ibm.softlayer.iscsi.service;

import com.ibm.softlayer.util.APIConstants;

public class EditIscsiNetworkStorageService extends AbstractgetIscsiNetworkStorageService{

	public EditIscsiNetworkStorageService(String username, String apikey) {
		super(username, apikey);
		// TODO Auto-generated constructor stub
	}

	public boolean editIscsiNetworkStorage(int iscsiVolumeId)throws Exception
	{
		return editNotesIscsiStorage(iscsiVolumeId, "notes", "Updated notes from code12",APIConstants.EDITOBJECT_API);
	}
}
