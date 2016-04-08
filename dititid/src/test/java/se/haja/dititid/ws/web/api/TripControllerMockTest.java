package se.haja.dititid.ws.web.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import se.haja.dititid.ws.AbstractControllerTest;
import se.haja.dititid.ws.model.Trip;
import se.haja.dititid.ws.service.TripService;

@Transactional
public class TripControllerMockTest extends AbstractControllerTest {

	private static final String EMPTY_FROM_ADDRESS = "";
	private static final String FROM_ADDRESS = "Barrstigen 19";
	private static final Boolean LEAVE_EARLIEST = Boolean.TRUE;
	private static final Boolean ARRIVE_LATEST = Boolean.FALSE;
	private static final String TO_ADDRESS = "Regeringsgatan 20";
	private static final Date TRIP_TIME = new Date();
	
	@Mock
	TripService tripService;

	@InjectMocks
	TripController tripController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		super.setUp(tripController);
	}

	@Test
    public void testGetTrips() throws Exception {

        // Create some test data
        Collection<Trip> list = getEntityListStubData();

        // Stub the tripService.getAllTrips method return value
        when(tripService.findAll()).thenReturn(list);

        // Perform the behavior being tested
        String uri = "/api/trip";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the tripService.getAllTrips method was invoked once
        verify(tripService, times(1)).findAll();

        // Perform standard JUnit assertions on the response
        assertEquals("failure - expected HTTP status 200", 200, status);
        assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

    }

    @Test
    public void testGetTrip() throws Exception {

        // Create some test data
        Long id = new Long(1);
        Trip entity = getEntityStubData();

        // Stub the tripService.getTrip method return value
        when(tripService.find(id)).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/trip/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the tripService.getTrip method was invoked once
        verify(tripService, times(1)).find(id);

        // Perform standard JUnit assertions on the test results
        assertEquals("failure - expected HTTP status 200", 200, status);
        assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);
    }

    @Test
    public void testGetTripNotFound() throws Exception {

        // Create some test data
        Long id = Long.MAX_VALUE;

        // Stub the tripService.getTrip method return value
        when(tripService.find(id)).thenReturn(null);

        // Perform the behavior being tested
        String uri = "/api/trip/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the tripService.getTrip method was invoked once
        verify(tripService, times(1)).find(id);

        // Perform standard JUnit assertions on the test results
        assertEquals("failure - expected HTTP status 404", 404, status);
        assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

    }

    @Test
    public void testCreateTrip() throws Exception {

        // Create some test data
        Trip entity = getEntityStubData();

        // Stub the tripService.create method return value
        when(tripService.createTrip(any(Trip.class))).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/trip";
        String inputJson = super.mapToJson(entity);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the tripService.create method was invoked once
        verify(tripService, times(1)).createTrip(any(Trip.class));

        // Perform standard JUnit assertions on the test results
        assertEquals("failure - expected HTTP status 201", 201, status);
        assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Trip createdEntity = super.mapFromJson(content, Trip.class);

        assertNotNull("failure - expected entity not null",
                createdEntity);
        assertNotNull("failure - expected id attribute not null",
                createdEntity.getId());
        assertEquals("failure - expected text attribute match",
                entity.getFromAddress(), createdEntity.getFromAddress());
    }

    @Test
    public void testUpdateTrip() throws Exception {

        // Create some test data
        Trip entity = getEntityStubData();
        entity.setFromAddress(entity.getFromAddress() + " test");
        Long id = new Long(1);

        // Stub the tripService.update method return value
        when(tripService.update(any(Trip.class))).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/trip/{id}";
        String inputJson = super.mapToJson(entity);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.put(uri, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the tripService.update method was invoked once
        verify(tripService, times(1)).update(any(Trip.class));

        // Perform standard JUnit assertions on the test results
        assertEquals("failure - expected HTTP status 200", 200, status);
        assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Trip updatedEntity = super.mapFromJson(content, Trip.class);

        assertNotNull("failure - expected entity not null",
                updatedEntity);
        assertEquals("failure - expected id attribute unchanged",
                entity.getId(), updatedEntity.getId());
        assertEquals("failure - expected text attribute match",
                entity.getFromAddress(), updatedEntity.getFromAddress());

    }

    @Test
    public void testDeleteTrip() throws Exception {

        // Create some test data
        Long id = new Long(1);

        // Perform the behavior being tested
        String uri = "/api/trip/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id))
                .andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the tripService.delete method was invoked once
        verify(tripService, times(1)).delete(id);

        // Perform standard JUnit assertions on the test results
        assertEquals("failure - expected HTTP status 204", 204, status);
        assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

    }
	
    private Collection<Trip> getEntityListStubData() {
        Collection<Trip> list = new ArrayList<Trip>();
        list.add(getEntityStubData());
        return list;
    }

    private Trip getEntityStubData() {
    	Trip entity = new Trip();
        entity.setId(1L);
        entity.setFromAddress(FROM_ADDRESS);
        entity.setToAddress(TO_ADDRESS);
        entity.setTripTime(TRIP_TIME);
        entity.setLeaveEarliest(LEAVE_EARLIEST);
        return entity;
    }

}
