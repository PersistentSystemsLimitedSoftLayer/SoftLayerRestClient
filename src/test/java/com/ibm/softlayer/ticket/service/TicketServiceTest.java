package com.ibm.softlayer.ticket.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.Arrays;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.ibm.softlayer.util.UnitTestConstants;

public class TicketServiceTest {
	
	private static String ticketId = null; 
	
	@Test
	public void testCreateTicket() throws Exception {		
		String assinedToUser="245236";
		String SubjectId="1522";
		String tittle="TestAutoTicket";
		String content="New Ticket is created.... generated for Testing Automation process";
		
		CreateTicketsService service = new CreateTicketsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject ticketObject = service.createTickets(assinedToUser, SubjectId, tittle, content);
		assertNotNull(ticketObject);
		assertEquals(assinedToUser, ticketObject.getString("assignedUserId"));	
		ticketId = ticketObject.getString("id");
	}	
	
	@Test
	public void testAddAnUpdateToTicket() throws Exception {			
		String content="This is Second update to this ticket. generated for Testing Automation process";		
		AddUpdateTicketService service = new AddUpdateTicketService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray ticketObject = service.addAnUpdateToTicket(ticketId, content);
		assertNotNull(ticketObject);
		assertEquals(content, ticketObject.getJSONObject(0).getString("entry"));		
	}
	
	@Test
	public void testGetTicketUpdates() throws Exception {
		GetTicketUpdatesService service = new GetTicketUpdatesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray ticketObject = service.getTicketUpdates(ticketId);		
		assertNotNull(ticketObject);		
		assertEquals(2, ticketObject.size());
	}
	
	@Test
	public void testGetTicketUpdatesWithObjectMasks() throws Exception {
		GetTicketUpdatesService service = new GetTicketUpdatesService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray ticketObject = service.getTicketUpdates(ticketId, Arrays.asList("entry"));		
		assertNotNull(ticketObject);
		assertEquals(2, ticketObject.size());
	}	
	

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
	
	@Test
	public void testListSubjects() throws Exception{
		ListSubjectsService service = new ListSubjectsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray array = service.list_subjects();
		assertNotNull(array);
	}
	
	@Test
	public void testListSubjectsWithObjectMask() throws Exception{
		ListSubjectsService service = new ListSubjectsService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONArray jsonObject = service.list_subjectsByObjectMask(Arrays.asList("group"));
		assertNotNull(jsonObject);
	}
	
	@Test
public void testUpdateTicketService() throws Exception {
		
		
		String assinedToUser="245240";
		String SubjectId="1522";
		String tittle="TestAutoTicket";
		String content="update ticket........... generated for Testing Automation process";
		UpdateTicketService updateTicketService = new UpdateTicketService(UnitTestConstants.SL_USERNAME, UnitTestConstants.SL_APIKEY);
		JSONObject jsonObject=updateTicketService.updateTicket(assinedToUser, SubjectId, tittle, content, ticketId);
		assertNotNull(jsonObject);
		assertEquals(ticketId, jsonObject.getString("id"));
	}
}
