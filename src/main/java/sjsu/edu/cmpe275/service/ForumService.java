package sjsu.edu.cmpe275.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ForumService {
	
	ResponseEntity<?> createMsg(Map<String, Object> reqBody);


//	ResponseEntity<?> getMsg(Long eventID);

	ResponseEntity<?> getMsgForSignUpForum(Long eventID, String forumType);

	ResponseEntity<?> getMsgForParticipantForum(Long eventID, String forumType);
}
