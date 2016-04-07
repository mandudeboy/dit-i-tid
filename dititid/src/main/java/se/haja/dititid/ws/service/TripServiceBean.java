package se.haja.dititid.ws.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.haja.dititid.ws.model.Trip;
import se.haja.dititid.ws.repository.TripRepository;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TripServiceBean implements TripService {

	@Autowired
	private TripRepository tripRepository;

	@Override
	public Collection<Trip> getAllTrips() {
		return tripRepository.findAll();
	}

	@Override
	@Cacheable(value = "trips", key = "#id")
	public Trip getTrip(Long id) {
		return tripRepository.findOne(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "trips", key = "#result.id")
	public Trip createTrip(Trip trip) {
		if (trip.getId() != null) {
			return null;
		}
		return tripRepository.save(trip);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "trips", key = "#trip.id")
	public Trip updateTrip(Trip trip) {
		if (getTrip(trip.getId()) == null) {
			return null;
		}
		return tripRepository.save(trip);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CacheEvict(value = "trips", key = "#id")
	public void deleteTrip(Long id) {
		tripRepository.delete(id);
	}

	@Override
	@CacheEvict(value = "trips", allEntries = true)
	public void evictCache() {
		
	}

}
