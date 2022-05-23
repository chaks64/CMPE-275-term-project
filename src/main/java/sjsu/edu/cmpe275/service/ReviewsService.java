package sjsu.edu.cmpe275.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface ReviewsService {
	ResponseEntity<?> postReview(Map<String, Object> reqBody);
	
	ResponseEntity<?> showReviews(String userId);
}
