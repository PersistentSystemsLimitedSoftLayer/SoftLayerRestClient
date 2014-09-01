package com.ibm.softlayer.ticket.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.apache.wink.json4j.JSONObject;
import org.apache.wink.json4j.compat.JSONArray;
import org.junit.Test;


import com.ibm.softlayer.util.UnitTestConstants;

public class TicketServiceTest {

	@Test
	public void testListAllTicket() throws Exception {
		getTickets service = new getTickets(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY,UnitTestConstants.SL_ACCOUNTID);
		org.apache.wink.json4j.JSONArray ticketArray = service.getAllTickets();
		System.out.println(ticketArray.toString());
		assertNotNull(ticketArray);
		//assertEquals(queueName, queueCreated.getString("name"));
		
	}
	
	@Test
	public void testListOpenTicket() throws Exception {
		getTickets service = new getTickets(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY,UnitTestConstants.SL_ACCOUNTID);
		org.apache.wink.json4j.JSONArray ticketArray = service.getOpenTickets();
		System.out.println(ticketArray.toString());
		assertNotNull(ticketArray);
		//assertEquals(queueName, queueCreated.getString("name"));
		
	}
	
	@Test
	public void testListClosedTicket() throws Exception {
		getTickets service = new getTickets(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY,UnitTestConstants.SL_ACCOUNTID);
		org.apache.wink.json4j.JSONArray ticketArray = service.getClosedTickets();
		System.out.println(ticketArray.toString());
		assertNotNull(ticketArray);
		//assertEquals(queueName, queueCreated.getString("name"));
		
	}
	
	@Test
	public void testGetTicketByID() throws Exception {
		String id="12139500";
		getTickets service = new getTickets(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY,UnitTestConstants.SL_ACCOUNTID);
		org.apache.wink.json4j.JSONObject ticketObject = service.getTicketByID(id);
		System.out.println(ticketObject.toString());
		assertNotNull(ticketObject);
		//assertEquals(queueName, queueCreated.getString("name"));
		
	}
	
	@Test
	public void testCreateTicket() throws Exception {
		String id="12139500";
		String AssinedToUser="245236";
		String SubjectId="1522";
		String tittle="TestAutoTicket";
		String content="This is Second ticket. generated for Testing Automation process";
		createTickets service = new createTickets(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY,UnitTestConstants.SL_ACCOUNTID);
		org.apache.wink.json4j.JSONObject ticketObject = service.createTickets(AssinedToUser, SubjectId, tittle, content);
		System.out.println(ticketObject.toString());
		assertNotNull(ticketObject);
		//assertEquals(queueName, queueCreated.getString("name"));
		
	}
	
}
