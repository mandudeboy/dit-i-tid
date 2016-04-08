package se.haja.dititid.ws.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
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

	@Autowired
	private CounterService counterService;
	
	@Override
	public Collection<Trip> findAll() {
		counterService.increment("method.invoked.tripServiceBean.findAll");
		return tripRepository.findAll();
	}

	@Override
	@Cacheable(value = "trips", key = "#id")
	public Trip find(Long id) {
		counterService.increment("method.invoked.tripServiceBean.find");
		return tripRepository.findOne(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "trips", key = "#result.id")
	public Trip createTrip(Trip trip) {
		counterService.increment("method.invoked.tripServiceBean.create");
		if (trip.getId() != null) {
			return null;
		}
		return tripRepository.save(trip);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "trips", key = "#trip.id")
	public Trip update(Trip trip) {
		counterService.increment("method.invoked.tripServiceBean.update");
		if (find(trip.getId()) == null) {
			return null;
		}
		return tripRepository.save(trip);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CacheEvict(value = "trips", key = "#id")
	public void delete(Long id) {
		counterService.increment("method.invoked.tripServiceBean.delete");
		tripRepository.delete(id);
	}

	@Override
	@CacheEvict(value = "trips", allEntries = true)
	public void evictCache() {
		counterService.increment("method.invoked.tripServiceBean.evictAll");
	}

}
