package se.haja.dititid.ws.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Model object for trip building instructions used when sending incomplete trips.
 * @author Joel
 *
 */
@Entity
public class Trip {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String fromAddress;
	private String toAddress;
	private Date tripTime;
	private Boolean leaveEarliest; //Default is false = arriveLatest
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public Date getTripTime() {
		return tripTime;
	}
	public void setTripTime(Date tripTime) {
		this.tripTime = tripTime;
	}
	public Boolean getLeaveEarliest() {
		return leaveEarliest;
	}
	public void setLeaveEarliest(Boolean leaveEarliest) {
		this.leaveEarliest = leaveEarliest;
	}
}
