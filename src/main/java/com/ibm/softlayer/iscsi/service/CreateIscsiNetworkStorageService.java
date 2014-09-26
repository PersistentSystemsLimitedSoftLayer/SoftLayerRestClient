package com.ibm.softlayer.iscsi.service;

import org.apache.wink.json4j.JSONObject;

public class CreateIscsiNetworkStorageService  extends AbstractgetIscsiNetworkStorageService{

	public CreateIscsiNetworkStorageService(String username, String apikey) {
		super(username, apikey);
		// TODO Auto-generated constructor stub
	}
	public JSONObject createiSCSIStorage() throws Exception
	{
		
		
		return createIscsi();
		
		
	}

}
