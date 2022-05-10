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

	public long getUserID() {
		return userId;
	}

	public void setUserID(long userId) {
		this.userId = userId;
	}

	public long getEventID() {
		return eventID;
	}

	public void setEventID(long eventID) {
		this.eventID = eventID;
	}
	
}
