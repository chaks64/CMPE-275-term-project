package sjsu.edu.cmpe275.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import sjsu.edu.cmpe275.model.ConfirmationToken;
import sjsu.edu.cmpe275.model.Event;
import sjsu.edu.cmpe275.repository.EventRepository;
import sjsu.edu.cmpe275.service.EventService;

@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventDetails {

    @Autowired
    private EventService eventService;

    @GetMapping("/details/{id}")
    private ResponseEntity<?> listEventDetails(@PathVariable("id") long eventId) {
        return eventService.listEvents();
    }

}
