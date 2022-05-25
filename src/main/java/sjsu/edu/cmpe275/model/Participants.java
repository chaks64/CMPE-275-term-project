package sjsu.edu.cmpe275.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDateTime;

@Entity
@IdClass(ParticipantsID.class)
public class Participants {
	@Id
	private long userId;
	
	@Id
	private long eventID;
	
	private String status;

	private LocalDateTime signUpTime;

	private LocalDateTime statusUpdateTime;

	public Participants() {
		// TODO Auto-generated constructor stub
	}
	
	public Participants(long userId, long eventID, String status, LocalDateTime signUpTime, LocalDateTime statusUpdateTime) {
		super();
		this.userId = userId;
		this.eventID = eventID;
		this.status = status;
		this.signUpTime = signUpTime;
		this.statusUpdateTime = statusUpdateTime;
	}

	public long getEventID() {
		return eventID;
	}

	public void setEventID(long eventID) {
		this.eventID = eventID;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getSignUpTime() {
		return signUpTime;
	}

	public void setSignUpTime(LocalDateTime signUpTime) {
		this.signUpTime = signUpTime;
	}

	@Override
	public String toString() {
		return "Participants{" +
				"userId=" + userId +
				", eventID=" + eventID +
				", status='" + status + '\'' +
				", signUpTime=" + signUpTime +
				", statusUpdateTime=" + statusUpdateTime +
				'}';
	}

	public LocalDateTime getStatusUpdateTime() {
		return statusUpdateTime;
	}

	public void setStatusUpdateTime(LocalDateTime statusUpdateTime) {
		this.statusUpdateTime = statusUpdateTime;
	}
	
}
