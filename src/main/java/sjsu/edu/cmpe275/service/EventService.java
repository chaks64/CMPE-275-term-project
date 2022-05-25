package sjsu.edu.cmpe275.service;

import java.time.LocalDateTime;
import java.util.Map;

import com.google.api.client.util.DateTime;
import org.springframework.http.ResponseEntity;

public interface EventService {
	ResponseEntity<?> listEvents();
	
	ResponseEntity<?> createEvent(Map<String, Object> reqBody);
	
	ResponseEntity<?> participateEvent(Map<String, Object> reqBody);
	
	ResponseEntity<?> listApprovals(String userid, String status);
	
	ResponseEntity<?> manageRequest(Map<String, Object> reqBody);
	
	ResponseEntity<?> listMyevents(String userid);
	
	ResponseEntity<?> eventDetails(Long eventid);
  
	ResponseEntity<?> searchEvents(Map<String, Object> reqBody);
	
	ResponseEntity<?> participatedEvent(String userid);

	ResponseEntity<?> getEventsForSystemReport();
}
