package se.haja.dititid.ws.actuator.health;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import se.haja.dititid.ws.model.Trip;
import se.haja.dititid.ws.service.TripService;

@Component
public class TripsHealthIndicator implements HealthIndicator {

	
	@Autowired
	private TripService tripService;
	
	@Override
	public Health health() {
		Collection<Trip> trips = tripService.findAll();
		
		if (trips == null || trips.size() == 0) {
			Health.down().withDetail("count", 0).build();
		}
		return Health.up().withDetail("count", trips.size() ).build();
	}

}
