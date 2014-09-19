package com.ibm.softlayer.iscsi.service;

import java.util.List;

import org.apache.wink.json4j.JSONObject;

import com.ibm.softlayer.util.APIConstants;

public class GetOneIscsiNetworkStorageService extends AbstractgetIscsiNetworkStorageService {

	public GetOneIscsiNetworkStorageService(String username, String apikey) {
		super(username, apikey);
		// TODO Auto-generated constructor stub
	}

	public  JSONObject getOneIscsiNetworkStorage(int iscsiID) throws Exception
	{
		return getIscsiById(iscsiID, APIConstants.GETOBJECT_API, null);
	}

	
	public  JSONObject getOneIscsiNetworkStorage(int iscsiID,List<String> objectMask) throws Exception
	{
		return getIscsiById(iscsiID, APIConstants.GETOBJECT_API, objectMask);
	}
}
