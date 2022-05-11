package sjsu.edu.cmpe275.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import sjsu.edu.cmpe275.model.Event;
import sjsu.edu.cmpe275.repository.EventRepository;
import sjsu.edu.cmpe275.service.EventService;

@Controller
@RequestMapping("/event")
@CrossOrigin
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private EventRepository eventRepo;
	
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
	
	@GetMapping(value = "/mylist")
	public ResponseEntity<?> getMyEvent(@PathVariable String userid){
		return eventService.listEvents();
	}


}
