package com.ibm.softlayer.util;

import java.util.List;

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
}
