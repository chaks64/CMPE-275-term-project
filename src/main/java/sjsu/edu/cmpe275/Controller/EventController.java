package sjsu.edu.cmpe275.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping(value = "/search")
	public ResponseEntity<?> searchEvents(@RequestBody Map<String, Object> inputJson){
//		return eventService.createEvent(inputJson);
		return new ResponseEntity<>("All  OK", HttpStatus.OK);
	}

}
