package sjsu.edu.cmpe275.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sjsu.edu.cmpe275.RequestModel.ErrorResponse;
import sjsu.edu.cmpe275.model.Address;
import sjsu.edu.cmpe275.model.Event;
import sjsu.edu.cmpe275.repository.EventRepository;
import sjsu.edu.cmpe275.service.EventService;

@Service
public class EventServiceImpl implements EventService{
	
	@Autowired
	private EventRepository eventRepo;

	@Override
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
			
			event.setStartDate(start);
			event.setEndtDate(end);
			event.setDeadline(null);
			
			Map<String, String> addressMap =(Map<String, String>) reqBody.get("address");
			Address address = new Address();
			address.setCity(addressMap.get("street"));
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
	
	

}
