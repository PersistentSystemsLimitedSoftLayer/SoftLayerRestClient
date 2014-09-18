package com.ibm.softlayer.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;

/**
 * The Class SLAPIUtil.
 */
public class SLAPIUtil {

	/**
	 * Process object masks.
	 *
	 * @param url the url
	 * @param objectMasks the object masks
	 */
	public static void processObjectMasks(StringBuffer url, List<String> objectMasks) {
		//setting the object masks
		if(objectMasks != null && objectMasks.size() > 0){
			StringBuffer mask = new StringBuffer();
			for(String maskVal : objectMasks) {
				if(mask.toString().trim().length() > 0){
					mask.append(";");
				}
				mask.append(maskVal);
			}
			
			//append the mask to the URL
			if(mask.toString().trim().length() > 0){
				url.append("?").append("objectMask=").append(mask.toString());
			}
		}
	}
	
	/**
	 * Process object masks.
	 *
	 * @param objectMasks the object masks
	 * @return the string
	 */
	public static String processObjectMasks(List<String> objectMasks) {
		//setting the object masks
		StringBuffer mask = new StringBuffer();
		if(objectMasks != null && objectMasks.size() > 0){
			for(String maskVal : objectMasks) {
				if(mask.toString().trim().length() > 0){
					mask.append(";");
				}
				mask.append(maskVal);
			}					
		}
		return mask.toString();
	}
	
	/**
	 * Process object filters.
	 *
	 * @param apiClient the api client
	 * @param filterKey the filter key
	 * @param filterValue the filter value
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String processObjectFilters(String apiClient, String filterKey, String filterValue) throws Exception {		
		String objectFilter = null;
		if((filterKey != null && filterKey.trim().length() > 0) && (filterValue !=null && filterValue.length() > 0)){
			JSONObject operation = new JSONObject();
			try {
				operation.put("operation", filterValue);
				
				JSONObject requestJson = new JSONObject();
				requestJson.put(filterKey, operation);
				
				if(apiClient != null && apiClient.trim().length() > 0){
					JSONObject filter = new JSONObject();
					filter.put(apiClient, requestJson);			
					
					objectFilter = filter.toString();
				} else {
					objectFilter = requestJson.toString();
				}				
			} catch (JSONException e) {
				e.printStackTrace();
				throw e;
			}						
		}
		return objectFilter;
	}
	
	/**
	 * Process request parameters.
	 *
	 * @param requestParamsMap the request params map
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String processRequestParameters(Map<String, String> requestParamsMap) throws Exception {
		StringBuffer requestParams = new StringBuffer();
		if(requestParamsMap != null && requestParamsMap.size() > 0){
			for(Map.Entry<String, String> entry : requestParamsMap.entrySet()) {				
				try {
					if(requestParams.toString().length() > 0){
						requestParams.append("&");
					}
					
					requestParams.append(entry.getKey()).append("=")
						.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					throw e;
				}
			}			
		}
		return requestParams.toString();
	}
}
