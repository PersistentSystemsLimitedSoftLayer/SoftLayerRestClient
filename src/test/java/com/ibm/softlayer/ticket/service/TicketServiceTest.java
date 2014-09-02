package com.ibm.softlayer.ticket.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;



import com.ibm.softlayer.util.UnitTestConstants;

public class TicketServiceTest {
	
	@Test
	public void testCreateTicket() throws Exception {		
		String assinedToUser="245236";
		String SubjectId="1522";
		String tittle="TestAutoTicket";
		String content="This is Second ticket. generated for Testing Automation process";
		
		CreateTicketsService service = new CreateTicketsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject ticketObject = service.createTickets(assinedToUser, SubjectId, tittle, content);
		assertNotNull(ticketObject);
		assertEquals(assinedToUser, ticketObject.getString("assignedUserId"));		
	}
	
	@Test
	public void testListOpenTicket() throws Exception {
		GetTicketsService service = new GetTicketsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray ticketArray = service.getOpenTickets();
		assertNotNull(ticketArray);
	}
	
	@Test
	public void testListClosedTicket() throws Exception {
		GetTicketsService service = new GetTicketsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray ticketArray = service.getClosedTickets();
		assertNotNull(ticketArray);
	}
	
	@Test
	public void testListAllTicket() throws Exception {
		GetTicketsService service = new GetTicketsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray ticketArray = service.getAllTickets();
		assertNotNull(ticketArray);
	}
	
/*
	
	
	
	
	
	
	@Test
	public void testGetTicketByID() throws Exception {
		String id="12139500";
		getTickets service = new getTickets(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY,UnitTestConstants.SL_ACCOUNTID);
		org.apache.wink.json4j.JSONObject ticketObject = service.getTicketByID(id);
		System.out.println(ticketObject.toString());
		assertNotNull(ticketObject);
		//assertEquals(queueName, queueCreated.getString("name"));
		
	}
	*/

	
}
