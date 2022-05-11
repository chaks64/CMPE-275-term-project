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

	@PostMapping(value = "/search")
	public ResponseEntity<?> searchEvents(@RequestBody Map<String, Object> inputJson){
		Map<String, Object> response = new HashMap<>();
//		return eventService.createEvent(inputJson);
		Set<String> inputKeys = inputJson.keySet();
		if(!(inputKeys.contains("location") && inputKeys.contains("status") && inputKeys.contains("startTime") && inputKeys.contains("endTime") && inputKeys.contains("keyword") && inputKeys.contains("organizer")) ) {
			response.put("status", 400);
			response.put("success", false);
			response.put("message", "All parameters are needed to perform search. Send default parameters if needed.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
//		System.out.println(eventRepo.findByDescription("12345"));
//		String dum = (String) inputJson.get("location"); 
		String dum1 = (String) inputJson.get("keyword"); 
		System.out.println("-----" + dum1);
		List<Event> x = eventRepo.findByDescriptionIgnoreCaseContainsOrTitleIgnoreCaseContains(dum1, dum1);
		for(Event e: x) { 
			System.out.println(e.getDescription());
		}
		return new ResponseEntity<>("All  OK", HttpStatus.OK);
	}

}
