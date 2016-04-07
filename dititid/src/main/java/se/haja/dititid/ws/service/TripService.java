package se.haja.dititid.ws.service;

import java.util.Collection;

import se.haja.dititid.ws.model.Trip;

public interface TripService {
	
	Collection<Trip> getAllTrips();
	
	Trip createTrip(Trip trip);

	Trip getTrip(Long id);
	
	Trip updateTrip(Trip trip);
	
	void deleteTrip(Long id);
	
	void evictCache();
}
