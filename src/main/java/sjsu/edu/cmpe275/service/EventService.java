package sjsu.edu.cmpe275.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface EventService {
<<<<<<< Updated upstream
	ResponseEntity<?> listEvents();
	
	ResponseEntity<?> createEvent(Map<String, Object> reqBody);
=======
	public abstract ResponseEntity<?> listEventDetails(Long id);

>>>>>>> Stashed changes
}
