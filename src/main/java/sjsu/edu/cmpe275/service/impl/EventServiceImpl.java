package sjsu.edu.cmpe275.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import sjsu.edu.cmpe275.model.Event;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sjsu.edu.cmpe275.model.Event;
import sjsu.edu.cmpe275.model.Participants;
import sjsu.edu.cmpe275.model.User;
import sjsu.edu.cmpe275.RequestModel.ErrorResponse;
import sjsu.edu.cmpe275.model.Address;
import sjsu.edu.cmpe275.repository.EventRepository;
import sjsu.edu.cmpe275.repository.ParticipantRepository;
import sjsu.edu.cmpe275.repository.UserRepository;
import sjsu.edu.cmpe275.service.EventService;
import org.springframework.web.client.HttpClientErrorException;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;

import sjsu.edu.cmpe275.ErrorHandler.ErrorExceptionHandler;
import sjsu.edu.cmpe275.ErrorHandler.BadRequest;
import sjsu.edu.cmpe275.model.Event;

@Service
public class EventServiceImpl implements EventService{
	
	@Autowired
	private EventRepository eventRepo;
	
	@Autowired
	private ParticipantRepository participantRepo;
	
	@Autowired
	private UserRepository userRepo;

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
			LocalDateTime deadline = LocalDateTime.parse((CharSequence) reqBody.get("deadline"));
			
			Long userid = Long.parseLong((String) reqBody.get("userid"));
			User user = userRepo.findByUserId(userid);
			if(user == null) {
				ErrorResponse errorResponse = new ErrorResponse("404", "User not found");
				return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
			}
			event.setUser(user);
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
				ErrorResponse errorResponse = new ErrorResponse("E02", "Unable to create event");
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


	@Override
	public ResponseEntity<?> participateEvent(Map<String, Object> reqBody) {
		System.out.println("in register for event "+ reqBody);
		try {
			Participants participants = new Participants();
			Integer integer = (Integer) reqBody.get("userid");
			Long userid = new Long(integer);
			participants.setUserId(userid);
			if(userRepo.findByUserId(userid) == null) {
				ErrorResponse errorResponse = new ErrorResponse("404", "User not found");
				return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
			}
			
			integer = (Integer) reqBody.get("eventid");
			Long eventid = new Long(integer);
			participants.setEventID(eventid);
			Event event = eventRepo.findByEventID(eventid);
			if(event == null) {
				ErrorResponse errorResponse = new ErrorResponse("404", "Event not found");
				return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
			}
			
			if(userid == event.getUser().getUserId()) {
				ErrorResponse errorResponse = new ErrorResponse("500", "Cannot participate in own event");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			if(event.getPolicy().equals("approval")){
				participants.setStatus("notapproved");
			} else {
				participants.setStatus("approved");
			}
			
			Participants newParticipants = participantRepo.save(participants);
			if(newParticipants == null) {
				ErrorResponse errorResponse = new ErrorResponse("E02", "Unable to register for event");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				return new ResponseEntity<>(newParticipants, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	//need to change the logic and rectify
	@Override
	public ResponseEntity<?> listApprovals(String eventid, String status) {
		System.out.println("here to show approval list");
		List<Participants> listParticipants = new ArrayList<>();
		try {
			Long eventId = Long.parseLong(eventid);
			List<User> users = new ArrayList<>();
			if(status.equals("notapproved")) {
				listParticipants = participantRepo.findByEventIDAndStatus(eventId, "notapproved");	
			} else if(status.equals("all")) {
				listParticipants = participantRepo.findByEventID(eventId);
			}
//			listParticipants = participantRepo.findByEventID(eventId);
			
			if(listParticipants==null || listParticipants.size()==0) {
				ErrorResponse error = new ErrorResponse("204", "No participants");
				return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
			} else {
				for(Participants p : listParticipants) {
					Long id = p.getUserId();
					User u = userRepo.findByUserId(id);
					if(u != null)
						users.add(u);
				}
				return new ResponseEntity<>(users, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> manageRequest(Map<String, Object> reqBody) {
		// TODO Auto-generated method stub
		System.out.println("in manage request "+reqBody);
		try {
			Long userid = Long.parseLong((String) reqBody.get("userid"));
			Participants participant = participantRepo.findByUserId(userid);
			if(participant == null) {
				ErrorResponse errorResponse = new ErrorResponse("404", "User not found");
				return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
			}
			Participants newParticipant = new Participants(participant.getUserId(), participant.getEventID(), (String) reqBody.get("status"));
			participantRepo.save(newParticipant);
			
			return new ResponseEntity<>(newParticipant, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
//	@Override
	public ResponseEntity<?> searchEvents(Map<String, Object> reqBody) {
//		String dum1 = (String) reqBody.get("keyword");
		String location = "%" + ((String) reqBody.get("location")).toLowerCase() + "%";

		String status = ((String) reqBody.get("status")).toLowerCase();
		String startDate = ((String) reqBody.get("startTime")).toLowerCase();
		// String format is "2022-05-04"
		String endtDate = ((String) reqBody.get("endtTime")).toLowerCase();
		String keyword = "%" + ((String) reqBody.get("keyword")).toLowerCase() + "%";
		String organizer = "%" + ((String) reqBody.get("organizer")).toLowerCase() + "%";
		List<Event> events = eventRepo.myfunction(location, status, startDate, endtDate, keyword);
		if(events==null || events.size()==0) {
			ErrorResponse error = new ErrorResponse("204", "No events");
			return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
		} else {
			for(Event e: events) {
				System.out.println(e);
			}
			return new ResponseEntity<>(events, HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<?> listMyevents(String userId) {
		System.out.println("in my events request "+userId);
		try {
			Long userid = Long.parseLong(userId);
			List<Event> eventsList = eventRepo.findAllByUserUserId(userid);

			if(eventsList==null || eventsList.size()==0) {
				ErrorResponse error = new ErrorResponse("204", "No participants");
				return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(eventsList, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@Override
	public ResponseEntity<?> eventDetails(Long eventid) {
		System.out.println("in my events details "+eventid);
		try {
			Event event = eventRepo.findOneByEventID(eventid);
			if(event==null) {
				ErrorResponse error = new ErrorResponse("204", "No event");
				return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
			} else {
				System.out.println(event.getParticipateUser().size());
				return new ResponseEntity<>(event, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> participatedEvent(String userId) {
		System.out.println("in my reg events "+userId);
		try {
			Long userid = Long.parseLong(userId);
			List<Event> eventsList = new ArrayList<>();
			List<Participants> list = participantRepo.findAllByUserId(userid);
			System.out.println(list);
			if(list==null || list.size()==0) {
				ErrorResponse error = new ErrorResponse("204", "No participants");
				return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
			} else {
				for(Participants p : list) {
					Long id = p.getEventID();
					Event e = eventRepo.findOneByEventID(id);
					if(e != null)
						eventsList.add(e);
				}
				return new ResponseEntity<>(eventsList, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
