package com.ibm.softlayer.ticket.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.Arrays;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;




import com.ibm.softlayer.util.UnitTestConstants;

public class TicketServiceTest {
	
	private static String ticketId = "12221584"; 
	
//	@Test
//	public void testCreateTicket() throws Exception {		
//		String assinedToUser="245236";
//		String SubjectId="1522";
//		String tittle="TestAutoTicket";
//		String content="This is Second ticket. generated for Testing Automation process";
//		
//		CreateTicketsService service = new CreateTicketsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
//		JSONObject ticketObject = service.createTickets(assinedToUser, SubjectId, tittle, content);
//		assertNotNull(ticketObject);
//		assertEquals(assinedToUser, ticketObject.getString("assignedUserId"));		
//	}
//
	@Test
	public void testGetTicketByID() throws Exception {
		GetTicketByIdService service = new GetTicketByIdService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject ticketObject = service.getTicketById(ticketId);		
		assertNotNull(ticketObject);
		assertEquals(ticketId, ticketObject.getString("id"));		
	}
	
	@Test
	public void testGetTicketByIDWithObjectMask() throws Exception {
		GetTicketByIdService service = new GetTicketByIdService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject ticketObject = service.getTicketById(ticketId, Arrays.asList("subjectId"));		
		assertNotNull(ticketObject);
		assertEquals(1, ticketObject.size());
		assertEquals("1522", ticketObject.getString("subjectId"));		
	}
	
	@Test
	public void testGetTicketStatusByID() throws Exception {
		GetTicketStatusService service = new GetTicketStatusService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject ticketObject = service.getTicketStatus(ticketId);		
		assertNotNull(ticketObject);		
	}
	
	@Test
	public void testGetAssignedUserTicket() throws Exception {
		GetAssignedUserToTicketService service = new GetAssignedUserToTicketService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject ticketObject = service.getAssignedUser(ticketId);		
		assertNotNull(ticketObject);		
	}
	
	@Test
	public void testGetAssignedUserTicketWithObjectMask() throws Exception {
		GetAssignedUserToTicketService service = new GetAssignedUserToTicketService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject ticketObject = service.getAssignedUser(ticketId, Arrays.asList("email"));		
		assertNotNull(ticketObject);
		assertEquals(1, ticketObject.size());
		assertNotNull(ticketObject.getString("email"));
	}
	
	@Test
	public void testGetTicketUpdates() throws Exception {
		GetTicketUpdatesService service = new GetTicketUpdatesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray ticketObject = service.getTicketUpdates(ticketId);		
		assertNotNull(ticketObject);		
	}
	
	@Test
	public void testGetTicketUpdatesWithObjectMasks() throws Exception {
		GetTicketUpdatesService service = new GetTicketUpdatesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray ticketObject = service.getTicketUpdates(ticketId, Arrays.asList("entry"));		
		assertNotNull(ticketObject);
	}
	
	@Test
	public void testListOpenTicket() throws Exception {
		GetOpenTicketsService service = new GetOpenTicketsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray ticketArray = service.getOpenTickets();
		assertNotNull(ticketArray);
	}
	
	@Test
	public void testListClosedTicket() throws Exception {
		GetClosedTicketsService service = new GetClosedTicketsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray ticketArray = service.getClosedTickets();
		assertNotNull(ticketArray);
	}
	
	@Test
	public void testListAllTicket() throws Exception {
		GetTicketsService service = new GetTicketsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray ticketArray = service.getAllTickets();
		assertNotNull(ticketArray);
	}		
}
