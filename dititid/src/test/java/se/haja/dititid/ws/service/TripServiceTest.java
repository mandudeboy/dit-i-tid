package se.haja.dititid.ws.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import se.haja.dititid.ws.AbstractTest;
import se.haja.dititid.ws.model.Trip;

@Transactional
public class TripServiceTest extends AbstractTest{

	@Autowired
	TripService service;
	
	@Before
	public void setUp() {
		service.evictCache();
	}
	
	@After
	public void tearDown() {
		//Clean up
	}
	
	@Test
	public void testGetAllTrips() {
		Collection<Trip> trips = service.getAllTrips();
		assertNotNull(trips);
		assertEquals(2, trips.size());
	}
	
	@Test
	public void testGetTrip() {
		Trip trip = service.getTrip(1l);
		assertNotNull(trip);
	}
	
//	@Test
//	public void testCreateTrip() {
//		Trip trip = new Trip();
//		trip.setFromAddress("Barrstigen 19");
//		Trip trip = service.createTrip(trip);
//		assertNotNull(trip);
//	}
//	
//	@Test
//	public void testGetTrip() {
//		Trip trip = service.getTrip(1l);
//		assertNotNull(trip);
//	}
//	
//	@Test
//	public void testGetTrip() {
//		Trip trip = service.getTrip(1l);
//		assertNotNull(trip);
//	}
}
