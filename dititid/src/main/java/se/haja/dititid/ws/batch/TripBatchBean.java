package se.haja.dititid.ws.batch;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import se.haja.dititid.ws.model.Trip;
import se.haja.dititid.ws.service.TripService;

@Component
@Profile("batch")
public class TripBatchBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TripService tripService;

	/**
	 * Clears all passed trips from the database, runs nightly.
	 */
	@Scheduled(cron = "${batch.trip.cron}")
	//@Scheduled(initialDelay = 5000, fixedDelay = 15000)
	public void clearPassedTripsCronJob() {
		logger.info("> cronJob");

		//TODO: Implement
		// Scheduled logic
		//tripService.clearPassedTrips();
		
		
		//TODO: Remove
		Collection<Trip> trips = tripService.getAllTrips();
		logger.info("There are {} trips in the database", trips.size());

		logger.info("< cronJob");
	}
	
	
}
