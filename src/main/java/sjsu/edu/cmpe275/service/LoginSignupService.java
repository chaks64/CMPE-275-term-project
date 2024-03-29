package sjsu.edu.cmpe275.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import sjsu.edu.cmpe275.model.User;

public interface LoginSignupService {
	ResponseEntity<?> createUser(Map<String, Object> reqBody);
	
	ResponseEntity<?> loginUser(Map<String, Object> reqBody);
	
	ResponseEntity<?> googleSignon(Map<String, Object> reqBody);
	
	ResponseEntity<?> googleSignup(Map<String, Object> reqBody);
}
