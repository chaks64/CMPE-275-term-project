package sjsu.edu.cmpe275.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface EventService {
	ResponseEntity<?> listEvents();
	
	ResponseEntity<?> createEvent(Map<String, Object> reqBody);
	
	ResponseEntity<?> participateEvent(Map<String, Object> reqBody);
	
	ResponseEntity<?> listApprovals(String userid, String status);
	
	ResponseEntity<?> manageRequest(Map<String, Object> reqBody);

	ResponseEntity<?> searchEvents(Map<String, Object> reqBody);
}
