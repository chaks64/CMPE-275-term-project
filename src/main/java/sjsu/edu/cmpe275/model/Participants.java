package sjsu.edu.cmpe275.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
	
@Entity
@IdClass(ParticipantsID.class)
public class Participants {
	@Id
	private long userId;
	
	@Id
	private long eventID;
	
	private String status;

	public Participants() {
		// TODO Auto-generated constructor stub
	}
	
	public Participants(long userId, long eventID, String status) {
		super();
		this.userId = userId;
		this.eventID = eventID;
		this.status = status;
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
	
}
