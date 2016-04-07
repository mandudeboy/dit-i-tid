package se.haja.dititid.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.haja.dititid.ws.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

}
