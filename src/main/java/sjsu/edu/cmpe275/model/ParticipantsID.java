package sjsu.edu.cmpe275.model;

import java.io.Serializable;
import java.util.Objects;

public class ParticipantsID implements Serializable{
	private long userId;
	private long eventID;
	
	public ParticipantsID(long userId, long eventID) {
		super();
		this.userId = userId;
		this.eventID = eventID;
	}
	
	public ParticipantsID() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(eventID, userId);
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
		return eventID == other.eventID && userId == other.userId;
	}
	
	

}
