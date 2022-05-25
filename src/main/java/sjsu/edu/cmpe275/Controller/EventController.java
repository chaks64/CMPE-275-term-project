package sjsu.edu.cmpe275.Controller;

import java.time.LocalDateTime;
import java.util.*;

import com.google.api.client.util.DateTime;
import net.minidev.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import sjsu.edu.cmpe275.service.EventService;

@Controller
@RequestMapping("/event")
@CrossOrigin
public class EventController {
	
	@Autowired
	private EventService eventService;

	@PostMapping(value = "/create")
	public ResponseEntity<?> createEvent(@RequestBody Map<String, Object> inputJson){
		return eventService.createEvent(inputJson);
	}
	
	@GetMapping(value = "/list")
	public ResponseEntity<?> getEvent(){
		return eventService.listEvents();
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<?> participateEvent(@RequestBody Map<String, Object> inputJson){
		return eventService.participateEvent(inputJson);
	}
	
	@GetMapping(value = "/partlist/{userid}/{status}")
	public ResponseEntity<?> getParticipants(@PathVariable String userid, @PathVariable String status){
		return eventService.listApprovals(userid, status);
	}
	
	@PostMapping(value = "/manage")
	public ResponseEntity<?> manageRequest(@RequestBody Map<String, Object> inputJson){
		return eventService.manageRequest(inputJson);
	}
	
	@GetMapping(value = "/mylist/{userid}")
	public ResponseEntity<?> getMyEvents(@PathVariable String userid){
		return eventService.listMyevents(userid);
	}
	
	@GetMapping(value = "/{eventid}")
	public ResponseEntity<?> eventDetails(@PathVariable Long eventid){
		System.out.println("here");
		return eventService.eventDetails(eventid);
	}

	@PostMapping(value = "/search")
	public ResponseEntity<?> searchEvents(@RequestBody Map<String, Object> inputJson){
		Map<String, Object> response = new HashMap<>();
//		return eventService.createEvent(inputJson);
		Set<String> inputKeys = inputJson.keySet();
		if(!(inputKeys.contains("location") && inputKeys.contains("status") && inputKeys.contains("startTime") && inputKeys.contains("endtTime") && inputKeys.contains("keyword") && inputKeys.contains("organizer")) ) {
			response.put("status", 400);
			response.put("success", false);
 			response.put("message", "All parameters are needed to perform search. Send default parameters if needed.");
 			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
 		}
// 		List<Event> x = eventRepo.findByDescriptionIgnoreCaseContainsOrTitleIgnoreCaseContains(dum1, dum1);
		return eventService.searchEvents(inputJson);
// 		return new ResponseEntity<>("All  OK", HttpStatus.OK);
 	}
	
	@GetMapping(value = "/part/{userid}")
	public ResponseEntity<?> partEvent(@PathVariable String userid){
		System.out.println("here");
		return eventService.participatedEvent(userid);
	}

	@GetMapping(value = "/systemReport")
	public ResponseEntity<?> getEventsForSystemReport(){

//		System.out.println("printing time "+LocalDateTime.now());
//		System.out.println("printing time again "+LocalDateTime.now().minusDays(90));
		return eventService.getEventsForSystemReport();
	}

}
