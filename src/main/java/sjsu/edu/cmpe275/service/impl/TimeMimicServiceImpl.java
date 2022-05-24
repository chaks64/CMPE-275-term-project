package sjsu.edu.cmpe275.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import sjsu.edu.cmpe275.RequestModel.ErrorResponse;
import sjsu.edu.cmpe275.model.Event;
import sjsu.edu.cmpe275.model.Participants;
import sjsu.edu.cmpe275.model.User;
import sjsu.edu.cmpe275.model.VirtualTime;
import sjsu.edu.cmpe275.repository.EventRepository;
import sjsu.edu.cmpe275.repository.ParticipantRepository;
import sjsu.edu.cmpe275.repository.UserRepository;

@Service
public class TimeMimicServiceImpl {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private ParticipantRepository participantRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private UserRepository userRepository;
	
	public ResponseEntity<?> updateTime(Map<String, Object> reqBody){
		System.out.println("in update time "+ reqBody);
		try {
			VirtualTime vTime = VirtualTime.getInstance();
			LocalDateTime vmTime = LocalDateTime.parse((CharSequence) reqBody.get("time"));
			vTime.setSystemTime(vmTime);
			List<Event> events = eventRepository.findAll();
			if(events.size() > 0) {
				for(Event e : events){
					if(vmTime.isAfter(e.getDeadline())) {
						cancelEvent(e);
					} else if(vmTime.isAfter(e.getEndtDate())) {
						finishEvent(e);
					} else if(vmTime.isAfter(e.getStartDate())) {
						startEvent(e);
					}
				}
			}
			return new ResponseEntity<>(vmTime, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public void cancelEvent(Event event) {
		List<Participants> participants = participantRepository.findByEventIDAndStatus(event.getEventID(), "approved");
		if(participants.size() < event.getMinParticpants() && event.getStatus().equals("active")) {
			event.setStatus("cancel");
			eventRepository.save(event);
			participants = participantRepository.findByEventID(event.getEventID());
			for(Participants p : participants) {
				User user = userRepository.findByUserId(p.getUserId());
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo(user.getEmail());
				mailMessage.setSubject("Event Cancelled!");
				mailMessage.setFrom("shahchintan64@gmail.com");
				mailMessage.setText("Event "+event.getTitle()+" has been cancelled");
				emailSenderService.sendEmail(mailMessage);
			}
		}
	}
	
	public void startEvent(Event event) {
		List<Participants> participants = participantRepository.findByEventIDAndStatus(event.getEventID(), "approved");
		if(participants.size() > 0 && event.getStatus().equals("active")) {
			for(Participants p : participants) {
				User user = userRepository.findByUserId(p.getUserId());
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo(user.getEmail());
				mailMessage.setSubject("Event Started!");
				mailMessage.setFrom("shahchintan64@gmail.com");
				mailMessage.setText("Event "+event.getTitle()+" has been started");
				emailSenderService.sendEmail(mailMessage);
			}
		}
	}
	
	public void finishEvent(Event event) {
		if(event.getStatus().equals("active")) {
			event.setStatus("finish");
			eventRepository.save(event);
		}
	}

	public ResponseEntity<?> getTime() {
		try {
			VirtualTime vTime = VirtualTime.getInstance();
			System.out.println("---------"+vTime.getSystemTime());
			Map<String, LocalDateTime> timeMap = new HashMap<>();
			timeMap.put("Time", vTime.getSystemTime());
			return new ResponseEntity<>(timeMap, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
