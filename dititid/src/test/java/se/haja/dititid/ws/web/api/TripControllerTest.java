package se.haja.dititid.ws.web.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import se.haja.dititid.ws.AbstractControllerTest;
import se.haja.dititid.ws.model.Trip;
import se.haja.dititid.ws.service.TripService;

@Transactional
public class TripControllerTest extends AbstractControllerTest {

	private static final String EMPTY_FROM_ADDRESS = "";
	private static final String FROM_ADDRESS = "Barrstigen 19";
	private static final Boolean LEAVE_EARLIEST = Boolean.TRUE;
	private static final Boolean ARRIVE_LATEST = Boolean.FALSE;
	private static final String TO_ADDRESS = "Regeringsgatan 20";
	private static final Date TRIP_TIME = new Date();
	@Autowired
	TripService tripService;

	@Before
	public void setUp() {
		super.setUp();
		tripService.evictCache();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetTrips() throws Exception {

		String uri = "/api/trip";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).
				accept(MediaType.APPLICATION_JSON)).
				andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		assertEquals(200, status);
		assertTrue(content.trim().length() > 0);
	}
	
	@Test
	public void testGetTrip() throws Exception {

		String uri = "/api/trip/{id}";
		Long id = 1l;
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).
				accept(MediaType.APPLICATION_JSON)).
				andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		assertEquals(200, status);
		assertTrue(content.trim().length() > 0);
	}
	
	@Test
	public void testGetTripNotFound() throws Exception {

		String uri = "/api/trip/{id}";
		Long id = Long.MAX_VALUE;
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).
				accept(MediaType.APPLICATION_JSON)).
				andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		assertEquals(404, status);
		assertTrue(content.trim().length() == 0);
	}
	
	@Test
	public void testCreateTrip() throws Exception {
		
		Trip trip = new Trip();
		trip.setFromAddress(EMPTY_FROM_ADDRESS);
		trip.setLeaveEarliest(LEAVE_EARLIEST);
		trip.setToAddress(TO_ADDRESS);
		trip.setTripTime(TRIP_TIME);
		String jsonTrip = super.mapToJson(trip);
		
		String uri = "/api/trip";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).
				contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON).content(jsonTrip)).
				andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		assertEquals(201, status);
		assertTrue(content.trim().length() > 0);
		
		Trip createdTrip = super.mapFromJson(content, Trip.class);
		assertNotNull(createdTrip);
		assertEquals(EMPTY_FROM_ADDRESS, createdTrip.getFromAddress());
		assertEquals(TO_ADDRESS, createdTrip.getToAddress());
		assertEquals(TRIP_TIME, createdTrip.getTripTime());
		assertEquals(LEAVE_EARLIEST, createdTrip.getLeaveEarliest());
	}

	@Test
	public void testUpdateTrip() throws Exception {
		
		Long id = 1l;
		Trip trip = tripService.find(id);
		trip.setFromAddress(FROM_ADDRESS);
		trip.setLeaveEarliest(ARRIVE_LATEST);
		trip.setToAddress(TO_ADDRESS);
		trip.setTripTime(TRIP_TIME);
		String jsonTrip = super.mapToJson(trip);
		
		String uri = "/api/trip/{id}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri, id).
				contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON).
				content(jsonTrip)).
				andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		assertEquals(200, status);
		assertTrue(content.trim().length() > 0);
		
		Trip createdTrip = super.mapFromJson(content, Trip.class);
		assertNotNull(createdTrip);
		assertEquals(FROM_ADDRESS, createdTrip.getFromAddress());
		assertEquals(TO_ADDRESS, createdTrip.getToAddress());
		assertEquals(TRIP_TIME, createdTrip.getTripTime());
		assertEquals(ARRIVE_LATEST, createdTrip.getLeaveEarliest());
	}
	
	@Test
	public void testDeleteTrip() throws Exception {
		
		String uri = "/api/trip/{id}";
		Long id = 1l;
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)).
				andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		assertEquals(204, status);
		assertTrue(content.trim().length() == 0);
		
		assertNull(tripService.find(id));
		
	}

}
