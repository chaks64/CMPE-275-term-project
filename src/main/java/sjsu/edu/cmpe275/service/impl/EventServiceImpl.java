package sjsu.edu.cmpe275.service.impl;

import java.sql.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.sql.Date;

import com.google.api.client.util.DateTime;
import org.apache.tomcat.jni.Local;
import sjsu.edu.cmpe275.model.Event;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import sjsu.edu.cmpe275.model.Participants;
import sjsu.edu.cmpe275.model.User;
import sjsu.edu.cmpe275.model.VirtualTime;
import sjsu.edu.cmpe275.RequestModel.ErrorResponse;
import sjsu.edu.cmpe275.model.Address;
import sjsu.edu.cmpe275.repository.EventRepository;
import sjsu.edu.cmpe275.repository.ParticipantRepository;
import sjsu.edu.cmpe275.repository.UserRepository;
import sjsu.edu.cmpe275.service.EventService;

@Service
public class EventServiceImpl implements EventService{
	
	@Autowired
	private EventRepository eventRepo;
	
	@Autowired
	private ParticipantRepository participantRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EmailSenderService emailSenderService;

	@Override
	public ResponseEntity<?> listEvents() {
		VirtualTime vTime = VirtualTime.getInstance();
		System.out.println("---------"+vTime.getSystemTime());
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
			
			
			
			Integer integer = (Integer) reqBody.get("userid");
//			Long userid = new Long(integer);
			Long userid = Long.valueOf(integer);
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
			event.setStatus("active");
			Event newEvent = eventRepo.save(event);
			
			if(newEvent == null) {
				ErrorResponse errorResponse = new ErrorResponse("E02", "Unable to create event");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo(user.getEmail());
				mailMessage.setSubject("New Event Created!");
				mailMessage.setFrom("shahchintan64@gmail.com");
				mailMessage.setText("Your event "+event.getTitle()+" has been created");
				emailSenderService.sendEmail(mailMessage);
				
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
//			Long userid = new Long(integer);
			Long userid = Long.valueOf(integer);
			participants.setUserId(userid);
			User user = userRepo.findByUserId(userid);
			if(user == null) {
				ErrorResponse errorResponse = new ErrorResponse("404", "User not found");
				return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
			}
			
			integer = (Integer) reqBody.get("eventid");
//			Long eventid = new Long(integer);
			Long eventid = Long.valueOf(integer);
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
			
			Participants alreadyParticipant = participantRepo.findByUserIdAndEventID(userid, eventid);
			if(alreadyParticipant != null) {
				ErrorResponse errorResponse = new ErrorResponse("E02", "Already registered for the event");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			Participants newParticipants = participantRepo.save(participants);
			if(newParticipants == null) {	
				ErrorResponse errorResponse = new ErrorResponse("E02", "Unable to register for event");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo(user.getEmail());
				mailMessage.setSubject("Signed-up for event!");
				mailMessage.setFrom("shahchintan64@gmail.com");
				mailMessage.setText("You have signed-up for "+event.getTitle());
				emailSenderService.sendEmail(mailMessage);
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
//			if(status.equals("notapproved")) {
//				listParticipants = participantRepo.findByEventIDAndStatus(eventId, "notapproved");	
//			} else if(status.equals("all")) {
//				listParticipants = participantRepo.findByEventIDAndStatus(eventId, "");
//			}
			listParticipants = participantRepo.findByEventIDAndStatus(eventId, status);
			
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
			Participants newParticipant = new Participants(participant.getUserId(), participant.getEventID(), (String) reqBody.get("status"), (LocalDateTime) reqBody.get("signUpTime"), (LocalDateTime) reqBody.get("statusUpdateTime"));
			participantRepo.save(newParticipant);
			User user = userRepo.findByUserId(participant.getUserId());
			Event event = eventRepo.findByEventID(participant.getEventID());
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("Signup status Update!");
			mailMessage.setFrom("shahchintan64@gmail.com");
			mailMessage.setText("Your request for "+event.getTitle()+" has been "+(String) reqBody.get("status"));
			emailSenderService.sendEmail(mailMessage);
			
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

		String status = "%" + ((String) reqBody.get("status")).toLowerCase() + "%";
		String startDate = ((String) reqBody.get("startTime"));
		// String format is "2022--05-04"
		String endtDate = ((String) reqBody.get("endtTime"));
		String keyword = "%" + ((String) reqBody.get("keyword")).toLowerCase() + "%";
		String organizer = "%" + ((String) reqBody.get("organizer")).toLowerCase() + "%";
		System.out.println(location + status + startDate +  endtDate + keyword);
//		List<Event> events = eventRepo.myfunction(location, status, startDate, endtDate, keyword);
		List<Event> events = eventRepo.myfunction(location, status, startDate, endtDate, keyword, organizer);
//		List<Event> events = new ArrayList<>();
		if(events==null || events.size()==0) {
			ErrorResponse error = new ErrorResponse("204", "No events");
			return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
		} else {
			for(Event e: events) {
				System.out.println(e.toString());
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
				if(event.getParticipateUser().size() > 0) {
					Set<User> pList = event.getParticipateUser();
					for(User u : event.getParticipateUser()) {
						Long userid = u.getUserId();
						Participants participants = participantRepo.findByUserIdAndEventID(userid, eventid);
						if(!participants.getStatus().equals("approved")) {
							pList.remove(u);
						}
					}
					event.setParticipateUser(pList);
				}
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
					if(p.getStatus().equals("approved")) {
						Long id = p.getEventID();
						Event e = eventRepo.findOneByEventID(id);
						if(e != null)
							eventsList.add(e);
					}
				}
				return new ResponseEntity<>(eventsList, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getEventsForSystemReport() {
		System.out.println("Here in getEventsForSystemReport service impl");

		VirtualTime vTime = VirtualTime.getInstance();
		LocalDateTime start_date = vTime.getSystemTime();
		LocalDateTime end_date = start_date.minusDays(90);

		HashMap<String, Integer> SystemReportHashMap = new HashMap<String, Integer>();

		try {

			List<Event> events = eventRepo.findAll();
			int noOfCreatedEvents = 0;
			int noOfPaidEvents = 0;
			double percentageOfPaidEvents = 0.0;
			int noOfCancelledEvents = 0;
			List <Participants> noOfParticipationRequests = new ArrayList<>();
			int totalNoOfMinParticipants = 0;
			int partReqDividedByTotalMinParts = 0;
			int noOfFinishedEvents = 0;
			int avgNumberOfParticipantsOfFinishedEvents = 0;
			List<Participants> p = new ArrayList<>();

			if(events==null || events.size()==0) {
				ErrorResponse error = new ErrorResponse("204", "No events");
				return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
			} else {
				for(int i=0;i<events.size();i++){

					if (events.get(i).getCreationTime().isAfter(end_date) && events.get(i).getCreationTime().isBefore(start_date)){
						noOfCreatedEvents += 1;
						if (events.get(i).getFees() > 0){
							noOfPaidEvents += 1;
						}

					}

					if (events.get(i).getStatus().equals("cancel") && events.get(i).getDeadline().isAfter(end_date) && events.get(i).getDeadline().isBefore(start_date)) {
						noOfCancelledEvents += 1;
						noOfParticipationRequests.addAll(participantRepo.findByEventID(events.get(i).getEventID()));
						totalNoOfMinParticipants += events.get(i).getMinParticpants();

					}

					if (events.get(i).getEndtDate().isAfter(end_date) && events.get(i).getEndtDate().isBefore(start_date)){
						 noOfFinishedEvents += 1;
						 p.addAll(participantRepo.findByEventID(events.get(i).getEventID()));

					}
				}

				System.out.println("No of created events : "+noOfCreatedEvents);
				System.out.println("No of paid events : "+noOfPaidEvents);
				percentageOfPaidEvents = Double.valueOf((noOfPaidEvents*100)/noOfCreatedEvents);
				System.out.println("percentage of paid events: "+percentageOfPaidEvents);

				System.out.println("No of cancelled events: "+noOfCancelledEvents);
				System.out.println("No of participation requests for cancelled events : "+noOfParticipationRequests.size());
				System.out.println("Total no of min parts of cancelled events: "+totalNoOfMinParticipants);
				partReqDividedByTotalMinParts = (noOfParticipationRequests.size() / totalNoOfMinParticipants);
				System.out.println("cancelled participation requests divided by total no of min participants :"+partReqDividedByTotalMinParts);

				System.out.println("No of finished events: "+noOfFinishedEvents);
				System.out.println("No of participants of cancelled events : "+p.size());
				avgNumberOfParticipantsOfFinishedEvents = p.size()/noOfFinishedEvents;
				System.out.println("Average number of participants of finished events: "+avgNumberOfParticipantsOfFinishedEvents);

				SystemReportHashMap.put("Number of created events",noOfCreatedEvents);
				SystemReportHashMap.put("Percentage of paid events", (int) percentageOfPaidEvents);

				SystemReportHashMap.put("Number of cancelled events", noOfCancelledEvents);
				SystemReportHashMap.put("Number of participation requests divided by total number of minimum participants for cancelled events", partReqDividedByTotalMinParts);

				SystemReportHashMap.put("Number of finished events", noOfFinishedEvents);
				SystemReportHashMap.put("Average number of participants of finished events", avgNumberOfParticipantsOfFinishedEvents);



				return new ResponseEntity<>(SystemReportHashMap, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
