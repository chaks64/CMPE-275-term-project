package sjsu.edu.cmpe275.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import sjsu.edu.cmpe275.service.impl.TimeMimicServiceImpl;

@Controller
@RequestMapping("/time")
@CrossOrigin
public class TimeController {
	
	@Autowired
	private TimeMimicServiceImpl timeMimicServiceImpl;
	
	@PostMapping(value = "/update")
	public ResponseEntity<?> createEvent(@RequestBody Map<String, Object> inputJson){
		return timeMimicServiceImpl.updateTime(inputJson);
	}
	
	@GetMapping(value = "/get")
	public ResponseEntity<?> getTime(){
		return timeMimicServiceImpl.getTime();
	}
}
