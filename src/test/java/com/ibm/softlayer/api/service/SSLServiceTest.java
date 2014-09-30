package com.ibm.softlayer.api.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.ssl.service.DeleteCertificateService;
import com.ibm.softlayer.ssl.service.EditCertificateService;
import com.ibm.softlayer.ssl.service.GetAllValidExpireCertificateService;
import com.ibm.softlayer.ssl.service.GetCertificationService;
import com.ibm.softlayer.util.UnitTestConstants;

public class SSLServiceTest {

	
	/**
	 * Get Certificate  Test.
	 * certType is Certificate type i.e All or Expire or Valid
	 * @throws Exception the exception
	 * 
	 * Note : This test is commented as there is no Certificates available with id 10599. 
	 * This test will work when there will be Certificates available on softlayer.
	 */
	/*@Test
	public void testgetCertification() throws Exception {
		String certId="10599";
		GetCertificationService service = new GetCertificationService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject Json = service.getCertification(certId);
		assertNotNull(Json);
		assertEquals(certId, Json.getString("id"));
	}*/
	
	
	/**
	 * List All Certificates Test.
	 * certType is Certificate type i.e All or Expire or Valid
	 * objectMask is list of properties to be mask
	 * @throws Exception the exception
	 */
	@Test
	public void testgetAllCertification() throws Exception {
		String certType="All";
		List<String> objectMask = null;
		objectMask = new ArrayList<String>();
		objectMask.add("id");
		objectMask.add("commonName");
		objectMask.add("validityDays");
		objectMask.add("notes");
		
		GetAllValidExpireCertificateService service = new GetAllValidExpireCertificateService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray jsonArr = service.getAllValidExpireCertificate(certType,objectMask);
		assertNotNull(jsonArr);
	}
	
	
	/**
	 * List Expire Certificates Test.
	 * certType is Certificate type i.e All or Expire or Valid
	 * objectMask is list of properties to be mask
	 * @throws Exception the exception
	 */
	@Test
	public void testgetExpireCertification() throws Exception {
		String certType="Expire";
		List<String> objectMask = null;
		GetAllValidExpireCertificateService service = new GetAllValidExpireCertificateService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray jsonArr = service.getAllValidExpireCertificate(certType,objectMask);
		assertNotNull(jsonArr);
	}
	
	
	/**
	 * List Valid Certificates Test.
	 * certType is Certificate type i.e All or Expire or Valid
	 * objectMask is list of properties to be mask
	 * @throws Exception the exception
	 */
	@Test
	public void testgetValidCertification() throws Exception {
		String certType="Valid";
		List<String> objectMask = null;
		GetAllValidExpireCertificateService service = new GetAllValidExpireCertificateService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray jsonArr = service.getAllValidExpireCertificate(certType,objectMask);
		assertNotNull(jsonArr);
	}
	
	
	/**
	 * Edit Certificate Test.
	 ** Note : This test is commented as there is no Certificates available with id 230942. 
	 * This test will work when there will be Certificates available on softlayer.
	 * Pass the Id of an Certificate to variable certId 
	 * @throws Exception the exception
	 */
	/*@Test
	public void testeditCertificateObject() throws Exception {
		String certId = "230942";
		Map<String,String> bodyelementMap = new HashMap<String,String>();
		bodyelementMap.put("notes", "This will edit the notes of certificate");
		
		EditCertificateService service = new EditCertificateService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		boolean isedited = service.editCertificateObject(certId, bodyelementMap);
		assertEquals(true, isedited);;
		System.out.println("isedited is ===="+isedited);
	}*/
	
	
	
	/**
	 * Delete Certificate Test.
	 ** Note : This test is commented as there is no Certificates available with id 230942. 
	 * This test will work when there will be Certificates available on softlayer.
	 * Pass the Id of an Certificate to variable certId 
	 * @throws Exception the exception
	 */
	/*@Test
	public void testdeleteCertificateObject() throws Exception {
		String certId = "230942";
		DeleteCertificateService service = new DeleteCertificateService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject json = service.deleteCertificateObject(certId);
		assertNotNull(json);
		System.out.println("json response for delete certificate is ===="+json);
	}*/
	

}
