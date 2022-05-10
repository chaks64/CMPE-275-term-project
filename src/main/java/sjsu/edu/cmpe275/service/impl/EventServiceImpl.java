	package sjsu.edu.cmpe275.service.impl;

<<<<<<< Updated upstream
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
=======
import java.util.Optional;
>>>>>>> Stashed changes

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
<<<<<<< Updated upstream
=======
import org.springframework.web.client.HttpClientErrorException;
import sjsu.edu.cmpe275.ErrorHandler.ErrorExceptionHandler;
import sjsu.edu.cmpe275.ErrorHandler.BadRequest;
import sjsu.edu.cmpe275.model.Event;
>>>>>>> Stashed changes

import sjsu.edu.cmpe275.RequestModel.ErrorResponse;
import sjsu.edu.cmpe275.model.Address;
import sjsu.edu.cmpe275.model.Event;
import sjsu.edu.cmpe275.repository.EventRepository;
import sjsu.edu.cmpe275.service.EventService;

@Service
public class EventServiceImpl implements EventService{
	
	@Autowired
	private EventRepository eventRepository;


//	@Override
//	public ResponseEntity<?> listEventDetails(Long id) {
//		System.out.println("IN event details ");
//		return null;
//	}

	@Override
<<<<<<< Updated upstream
	public ResponseEntity<?> listEvents() {
		
		try {
			List<Event> events = eventRepo.findAll();
			if(events==null || events.size()==0) {
				ErrorResponse error = new ErrorResponse("204", "No events");
				return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(events, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@Override
	public ResponseEntity<?> createEvent(Map<String, Object> reqBody) {
		System.out.println("in create event "+ reqBody);
		try {
			Event event = new Event();
			event.setTitle((String) reqBody.get("title"));
			event.setDescription((String) reqBody.get("desc"));
			LocalDateTime start = LocalDateTime.parse((CharSequence) reqBody.get("start"));
			LocalDateTime end = LocalDateTime.parse((CharSequence) reqBody.get("end"));
			LocalDateTime deadline = LocalDateTime.parse((CharSequence) reqBody.get("deadline"));
			
			event.setStartDate(start);
			event.setEndtDate(end);
			event.setDeadline(deadline);
			
			Map<String, String> addressMap =(Map<String, String>) reqBody.get("address");
			Address address = new Address();
			address.setStreet(addressMap.get("street"));
			address.setCity(addressMap.get("city"));
			address.setNumber(addressMap.get("number"));
			address.setState(addressMap.get("state"));
			address.setZipCode(addressMap.get("zipcode"));
			event.setAddress(address);
			
			event.setMinParticpants((int) reqBody.get("min"));
			event.setMaxParticpants((int) reqBody.get("max"));
			event.setFees((int) reqBody.get("fees"));
			event.setPolicy((String) reqBody.get("policy"));
			
			Event newEvent = eventRepo.save(event);
			
			if(newEvent == null) {
				ErrorResponse errorResponse = new ErrorResponse("E02", "Unable to create patient/admin");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	

=======
	public ResponseEntity<?> listEventDetails(Long id) {
		System.out.println(id);
		System.out.println("IN event details");
		Optional<Event> event= eventRepository.findById(id);
		try {
			if(event.isPresent()) {
				return new ResponseEntity<>(event.get(), HttpStatus.OK);
			}
			else {
				BadRequest errorObject = new BadRequest("404"," Sorry, the requested event " + id + " does not exist");
				ErrorExceptionHandler errorhandler = new ErrorExceptionHandler( errorObject);
				return new ResponseEntity<>(errorhandler, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}
>>>>>>> Stashed changes
}
