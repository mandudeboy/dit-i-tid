package se.haja.dititid.ws.service;

import java.util.Collection;

import se.haja.dititid.ws.model.Trip;

public interface TripService {
	
	Collection<Trip> findAll();
	
	Trip createTrip(Trip trip);

	Trip find(Long id);
	
	Trip update(Trip trip);
	
	void delete(Long id);
	
	void evictCache();
}
