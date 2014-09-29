package com.ibm.softlayer.iscsi.service;
import java.util.List;

import org.apache.wink.json4j.JSONArray;

import com.ibm.softlayer.util.APIConstants;

public class GetIscsiNetworkStorageService extends AbstractgetIscsiNetworkStorageService{
	
public GetIscsiNetworkStorageService(String username, String apikey) {
		super(username, apikey);
		// TODO Auto-generated constructor stub
	}

public 	JSONArray getAllIscsiNetworkStorage() throws Exception
{
	return getIscsiNetworkStorage(APIConstants.GET_NETWORK_STORAGE, null);
	
}
public 	JSONArray getAllIscsiNetworkStorage(List<String> objectMasks) throws Exception
{
	return getIscsiNetworkStorage(APIConstants.GET_NETWORK_STORAGE, objectMasks);
	
}
}
