package sjsu.edu.cmpe275.model;

import java.io.Serializable;
import java.util.Objects;

public class ParticipantsID implements Serializable{
	private long userID;
	private long eventID;
	
	public ParticipantsID(long userID, long eventID) {
		super();
		this.userID = userID;
		this.eventID = eventID;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(eventID, userID);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParticipantsID other = (ParticipantsID) obj;
		return eventID == other.eventID && userID == other.userID;
	}
	
	

}
