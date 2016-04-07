package se.haja.dititid.ws.web.api;

import java.math.BigInteger;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.haja.dititid.ws.model.Trip;
import se.haja.dititid.ws.service.TripService;

@RestController
public class TripController {

	@Autowired
	private TripService tripService;

	@RequestMapping(value = "/api/trip", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) {
		Trip savedTrip = tripService.createTrip(trip);
		return new ResponseEntity<Trip>(savedTrip, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/api/trip",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Trip>> retrieveAllTrip() {
		Collection<Trip> trips = tripService.getAllTrips();
		if (trips != null) {
			return new ResponseEntity<Collection<Trip>>(trips, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/api/trip/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Trip> retrieveTrip(@PathVariable Long id) {
		Trip trip = tripService.getTrip(id);
		if (trip != null) {
			return new ResponseEntity<Trip>(trip, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/api/trip/{id}", 
			method = RequestMethod.PUT, 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Trip> updateTrip(@PathVariable BigInteger id, @RequestBody Trip trip) {
		Trip updatedTrip = tripService.updateTrip(trip);
		if (updatedTrip == null) {
			return new ResponseEntity<Trip>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Trip>(updatedTrip, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/trip/{id}", 
			method = RequestMethod.DELETE)
	public ResponseEntity<Trip> deleteTrip(@PathVariable Long id) {
		tripService.deleteTrip(id);
		return new ResponseEntity<Trip>(HttpStatus.NO_CONTENT);
	}
	
}
