package sjsu.edu.cmpe275.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface EventService {
	ResponseEntity<?> listEvents();
}
