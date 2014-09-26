package com.ibm.softlayer.iscsi.service;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSON;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.client.BasicAuthorizationSLClient;
import com.ibm.softlayer.util.APIConstants;
import com.ibm.softlayer.util.SLAPIUtil;
import com.ibm.softlayer.util.URIGenerator;

public abstract class AbstractgetIscsiNetworkStorageService {
	
private String username = null;
	
	/** The api key. */
	private String apiKey = null;
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AbstractgetIscsiNetworkStorageService.class);
	
	/**
	 * Instantiates a new abstract get iSCSI network Storage details.
	 *
	 * @param username the username
	 * @param apikey the apikey
	 */

	public AbstractgetIscsiNetworkStorageService(String username, String apikey) {
		this.username = username;
		this.apiKey = apikey;
	}
	
 public JSONArray getIscsiNetworkStorage(String iscsiUrl,List<String> objectMasks) throws Exception
 {
	 logger.info("Executing getIscsiNetworkStorage: " + iscsiUrl + " for username: " + username);
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.ACCOUNT_ROOT_API));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(iscsiUrl);
	
		
		//execute the get iSCSI Network Storage call
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString(), objectMasks);
		
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed get iSCSI Network Storage call for username: " + username + ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONArray json = new JSONArray(response);
			logger.debug("getIscsiNetworkStorage: " + iscsiUrl + " JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve getIscsiNetworkStorage: " + iscsiUrl + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	 
	 }
 
 
 public JSONObject getIscsiById(int iscsiID , String iscsiUrl,List<String> objectMasks) throws Exception
 {
		
		//generate the get queues URL	
		StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.ISCSI_NETWORK_STORAGE_ROOT_URL));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(iscsiID).append("/").append(iscsiUrl);
		
		
		
		//execute the get get Iscsi Network Storage call
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		ClientResponse clientResponse = client.executeGET(url.toString(),objectMasks);
		String response = clientResponse.getEntity(String.class);
		logger.info("Executed  get Iscsi Network Storage By ID "+iscsiID+" for username: " + username + ", clientResponse: " + clientResponse.getStatusCode() + ", response: " + response);
		
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("getIscsiNetworkStorage By ID: " + iscsiUrl + " JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not retrieve getIscsiNetworkStorage: " + iscsiUrl + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	 
 }
 
 
 public JSONObject createSnapshot(int iscsiVloumeId, String notes ,String iscsiUrl) throws Exception
 {
	 StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.ISCSI_NETWORK_STORAGE_ROOT_URL));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(iscsiVloumeId).append("/").append(iscsiUrl);

		
		
		//execute POST call to create snapshot of Iscsi Network Storage 
		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		
		//ClientResponse clientResponse = client.executeGET(url.toString());
		JSONObject requestObject= new JSONObject();
		requestObject.put("notes", notes);
		
		//ClientResponse clientResponse =client.executePOST(url.toString(),null);
		ClientResponse clientResponse1 =client.executePOST(url.toString(), requestObject.toString());
		
		String response = clientResponse1.getEntity(String.class);
		logger.info("Executed  Create Snapshot of  Iscsi Network Storage of Volume "+iscsiVloumeId+" for username: " + username + ", clientResponse: " + clientResponse1.getStatusCode() + ", response: " + response);
		
		if(clientResponse1.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("getIscsiNetworkStorage By ID: " + iscsiUrl + " JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not Create snapshot of  iSCSI Network Storage: " + iscsiUrl + ": Code: " + clientResponse1.getStatusCode() + ", Reason: " + response);			
	 
 }
 
 public int getLocationID(String datacenterName) throws Exception
 {
	 
	int datacenterId = 0;
	 StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.SL_DATACENTER_LOCATION_ROOT_URL));
		if(!url.toString().endsWith("/")) {
			url.append("/");
		}
		url.append(APIConstants.GET_DATACENTER);

		BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);

		
		ClientResponse clientResponse =client.executeGET(url.toString());
		String response = clientResponse.getEntity(String.class);
		JSONArray response1=new JSONArray(response);
		for(int i=0;i<response1.length();i++)
		{
			System.out.println("datacenter name   "+response1.getJSONObject(i).getString("name"));
			System.out.println("datacenter name   "+response1.getJSONObject(i).getString("id"));
			if (response1.getJSONObject(i).getString("name").equals(datacenterName) )
			{
				datacenterId=response1.getJSONObject(i).getInt("id");
				 return datacenterId;
			}
		}
			
		throw new Exception("Could not found datacenter");
		 
 }
 
 
 public int find_item_prices(String categorycode  ,int cappacity)throws Exception
 {
	 StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.SL_PRODUCT_PACKAGE_ROOT_URL));
	 	if(!url.toString().endsWith("/")) {
	 		url.append("/");
	 	}
	 url.append("0").append("/").append(APIConstants.GET_ITEM);

	 BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
     
	 JSONArray objectFilterList =new JSONArray();
	 JSONObject objectFilter1 =new JSONObject();
	 objectFilter1.put("filterKey", "capacity");
	 objectFilter1.put("filterValue",cappacity);
	 objectFilter1.put("apiClient",""); 
	 
	 JSONObject objectFilter2 =new JSONObject();
	 objectFilter2.put("filterKey", "categoryCode");
	 objectFilter2.put("filterValue",categorycode);
	 objectFilter2.put("apiClient","categories"); 
	
	 objectFilterList.add(objectFilter2);
	 objectFilterList.add(objectFilter1);
	
	 
	
	 System.out.println("*url************    "+url);
	 String URL=processMultipleFilter(objectFilterList,"items",url);
	 System.out.println("*URL************    "+URL);
	 ClientResponse clientResponse =client.executeGET(URL);
	 String response = clientResponse.getEntity(String.class);
	 JSONArray responseobject=new JSONArray(response);
	
	JSONArray prices = (JSONArray) responseobject.getJSONObject(0).get("prices");
	int priceId=prices.getJSONObject(0).getInt("id");
	System.out.println(prices.getJSONObject(0).getInt("id"));
	 return priceId;

 }

 
 
 public String processMultipleFilter(JSONArray objectFilterList,String superApiClient,StringBuffer url) throws Exception
 {
	JSONObject objectFilter= new JSONObject();
	StringBuffer requestParams = new StringBuffer();
	JSONObject requestJson = new JSONObject();
	
			
			for (int i=0;i<objectFilterList.size();i++)
			{
				JSONObject operation = new JSONObject();
				operation.put("operation", objectFilterList.getJSONObject(i).get("filterValue"));
				
				
				requestJson.put(objectFilterList.getJSONObject(i).get("filterKey"), operation);

				if(objectFilterList.getJSONObject(i).get("apiClient") != null && objectFilterList.getJSONObject(i).get("apiClient").toString().trim().length() > 0){
					JSONObject filter = new JSONObject();
					filter.put(objectFilterList.getJSONObject(i).get("apiClient"), requestJson);			
					requestJson =filter;
					
				} 
			}
			objectFilter.put(superApiClient, requestJson);
			requestParams.append("?").append("objectFilter").append(URLEncoder.encode(objectFilter.toString(), "UTF-8"));
			System.out.println("request param        "+requestParams);
			url.append("?objectFilter=").append(URLEncoder.encode(objectFilter.toString(), "UTF-8"));
			return url.toString();
			}		
 	
		
 public JSONObject createSnapshotSpace(int iscsiVolumeId,int capacity) throws Exception
 {
	 
	int item_price =find_item_prices("iscsi_snapshot_space",capacity);

	JSONObject locationObject=getIscsiById(iscsiVolumeId,APIConstants.GETOBJECT_API , Arrays.asList("id","capacityGb","serviceResource.datacenter"));
	JSONObject snapshotSpaceOrder = new JSONObject();
	
	JSONObject  price=new JSONObject();
	price.put("id",item_price );
	JSONArray priceArray=new JSONArray();
	priceArray.add(price);
	snapshotSpaceOrder.put("complexType", "SoftLayer_Container_Product_Order_Network_Storage_Iscsi_SnapshotSpace");
	snapshotSpaceOrder.put("location" , locationObject.getJSONObject("serviceResource").getJSONObject("datacenter").getInt("id"));	
	snapshotSpaceOrder.put("packageId", 0);
	snapshotSpaceOrder.put("prices",priceArray);
	snapshotSpaceOrder.put("quantity", 1);
	snapshotSpaceOrder.put("volumeId", iscsiVolumeId);
	
	JSONArray multipleRequests = new JSONArray();
	multipleRequests.add(snapshotSpaceOrder);
	
	JSONObject requests = new JSONObject();
	requests.put("parameters", multipleRequests);
	
	
	 StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.SL_PRODUCT_ORDER));
	 	if(!url.toString().endsWith("/")) {
	 		url.append("/");
	 	}
	 url.append(APIConstants.SL_PLACE_ORDER);
	 
	

	 BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);

	ClientResponse clientResponse =client.executePOST(url.toString(), requests.toString());
	String response = clientResponse.getEntity(String.class);
	System.out.println("******************response for snapshot space *****************\n"+ response);
	if(clientResponse.getStatusCode() == 200){
		JSONObject json = new JSONObject(response);
		logger.debug("Create Snapshot space ofr volume: " + url + " JSON Response: " + response); 
		return json;			
	}
	
	throw new Exception("Could not Create snapshot space of  iSCSI Network Storage: " + url + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			

	}
 
 
 public JSONObject createIscsi() throws Exception
 {
	 
	 int item_price =find_item_prices("iscsi",20);
	 int  location=getLocationID("lon02");
		
		JSONObject snapshotSpaceOrder = new JSONObject();
		
		JSONObject  price=new JSONObject();
		price.put("id",item_price );
		JSONArray priceArray=new JSONArray();
		priceArray.add(price);
		snapshotSpaceOrder.put("complexType", "SoftLayer_Container_Product_Order_Network_Storage_Iscsi_SnapshotSpace");
		snapshotSpaceOrder.put("location" ,location);	
		snapshotSpaceOrder.put("packageId", 0);
		snapshotSpaceOrder.put("prices",priceArray);
		snapshotSpaceOrder.put("quantity", 1);
		
		JSONArray multipleRequests = new JSONArray();
		multipleRequests.add(snapshotSpaceOrder);
		
		JSONObject requests = new JSONObject();
		requests.put("parameters", multipleRequests);
		
		
		 StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.SL_PRODUCT_ORDER));
		 	if(!url.toString().endsWith("/")) {
		 		url.append("/");
		 	}
		 url.append(APIConstants.SL_PLACE_ORDER);
		 
		
		 BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
		
		ClientResponse clientResponse =client.executePOST(url.toString(), requests.toString());
		String response = clientResponse.getEntity(String.class);
	  
		if(clientResponse.getStatusCode() == 200){
			JSONObject json = new JSONObject(response);
			logger.debug("getIscsiNetworkStorage By ID: " + url + " JSON Response: " + response);
			return json;			
		}
		
		throw new Exception("Could not Create  iSCSI Network Storage: " + url + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	
 
 }
 
 
 
 public boolean deleteIscsi(int iscsiVolumeId, String deleteUrl) throws Exception
 {
	
	 StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.ISCSI_NETWORK_STORAGE_ROOT_URL));
	 	if(!url.toString().endsWith("/")) {
	 		url.append("/");
	 	}
	 url.append(iscsiVolumeId).append("/").append(deleteUrl);
	 
	
	 BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
	
	ClientResponse clientResponse =client.executeDELETE(url.toString());
	String response = clientResponse.getEntity(String.class);

	if(clientResponse.getStatusCode() == 200){
		
		return true;			
	}
	
	throw new Exception("Could not delete  iSCSI Network Storage: " + url + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	 

 }
 
 public boolean deleteSnapshot(int snapshotId, String deleteUrl) throws Exception
 {
	 
	 return deleteIscsi(snapshotId, deleteUrl);
 }
 
 
 
 public boolean restoreFromSnapshot(int iscsiVolumeId,int snapshotId,String restoreUrl) throws Exception
 {
	 logger.info("Executing Restore iscsi storage from snappshot: " + restoreUrl + " for username: " + username);
	 StringBuffer url = new StringBuffer(URIGenerator.getSLBaseURL(APIConstants.ISCSI_NETWORK_STORAGE_ROOT_URL));
		
	 	if(!url.toString().endsWith("/")) {
	 		url.append("/");
	 	}
	 url.append(iscsiVolumeId).append("/").append(restoreUrl);
	 
	
	 BasicAuthorizationSLClient client = new BasicAuthorizationSLClient(username, apiKey);
	
	 	
	 JSONArray parameters =new JSONArray();
	 parameters.add(snapshotId);
	 
	 JSONObject objectParameter=new JSONObject();
	 objectParameter.put("parameters", parameters);
	 
	ClientResponse clientResponse =client.executePOST(url.toString(),objectParameter.toString());
	String response = clientResponse.getEntity(String.class);

	if(clientResponse.getStatusCode() == 200){
		
		return true;			
	}
	
	throw new Exception("Could not restore  iSCSI Network Storage:  fron snapshot" + url + ": Code: " + clientResponse.getStatusCode() + ", Reason: " + response);			
	 

	 
	 
 }
 
 
 
 
	 }
		
 
	 

	 
 

 



