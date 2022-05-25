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
import sjsu.edu.cmpe275.model.Event;
import sjsu.edu.cmpe275.model.VirtualTime;
import sjsu.edu.cmpe275.RequestModel.ErrorResponse;
import sjsu.edu.cmpe275.model.Address;
import sjsu.edu.cmpe275.repository.EventRepository;
import sjsu.edu.cmpe275.repository.ParticipantRepository;
import sjsu.edu.cmpe275.repository.UserRepository;
import sjsu.edu.cmpe275.service.EventService;
import sjsu.edu.cmpe275.service.UserLevelReportService;

@Service
public class UserLevelReportServiceImpl implements UserLevelReportService {

    @Autowired
    private ParticipantRepository participantRepo;

    @Autowired
    private EventRepository eventRepo;
    
    @Override
    public ResponseEntity<?> getUserParticipationReport(Long userid) {
        VirtualTime vTime = VirtualTime.getInstance();
        LocalDateTime start_date = vTime.getSystemTime();
        LocalDateTime end_date = start_date.minusDays(90);
        HashMap<String, Integer> UserReportHashMap = new HashMap<String, Integer>();


        try {
//            List<Participants> participants = participantRepo.listAllEventsForUserInGivenTimeFrame(userid,end_date1,start_date1);
            List<Participants> participants = participantRepo.findAllByUserId(userid);

            int noOfSignedUpEvents = 0;
            int noOfRejectsAndApprovals = 0;
            Long eventid = null;
            List<Event> noOfFinishedEventsList = new ArrayList<>();
            int noOfFinishedEvents = 0;

            if(participants==null || participants.size()==0){
                ErrorResponse error = new ErrorResponse("204", "No events");
                return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
            }
            else{

                for(int i=0;i<participants.size();i++){
                    if (participants.get(i).getSignUpTime().isAfter(end_date) && participants.get(i).getSignUpTime().isBefore(start_date)){
                        noOfSignedUpEvents += 1;
                    }

                    if (participants.get(i).getStatusUpdateTime().isAfter(end_date) && participants.get(i).getStatusUpdateTime().isBefore(start_date)){
                        noOfRejectsAndApprovals += 1;
                    }

                    eventid = participants.get(i).getEventID();
                    noOfFinishedEventsList.addAll(eventRepo.findAllByEventID(eventid));
                }
                for(int j=0; j< noOfFinishedEventsList.size();j++){
                    if(noOfFinishedEventsList.get(j).getEndtDate().isAfter(end_date) && noOfFinishedEventsList.get(j).getEndtDate().isBefore(start_date)){
                        noOfFinishedEvents += 1;
                    }
                }

                System.out.println("No of signed up events: "+noOfSignedUpEvents);
                System.out.println("No of rejects and approvals: "+noOfRejectsAndApprovals);
                System.out.println("No of finished events: "+noOfFinishedEvents);

                UserReportHashMap.put("No of signed up events",noOfSignedUpEvents);
                UserReportHashMap.put("No of rejects and approvals",noOfRejectsAndApprovals);
                UserReportHashMap.put("No of finished events",noOfFinishedEvents);

                return new ResponseEntity<>(UserReportHashMap, HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    public ResponseEntity<?> getUserOrganizerReport(Long userid){
    	DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
    	//set the start and end date
        LocalDateTime start_date =  LocalDateTime.now();
        String start_date1 = start_date.format(outputFormatter);
        System.out.println("start_date: "+start_date1);
        LocalDateTime end_date = LocalDateTime.now().minusDays(90);
        String end_date1 = end_date.format(outputFormatter);
        
        Map<String, Object> response = new HashMap<>();
        
        // 1. Number of created events (based on creation time) and the percentage of paid events.
        Integer numOfEventsCreated = 0;
        Integer totalNumberOfPaidEvents = 0;
        double paidEventsPercent;
        
        // 2. Number of canceled events (based on registration deadline) and total number of participation requests (regardless of approval or not) divided by the total number of minimum participants for such events.
        Integer numOfCancelledEvents = 0;
        Integer totalNumberOfCancelledEventsParticipants = 0;
        Integer totalNumberOfCancelledEventsMinimumParticipants = 0;
        double cancelledEventsFinalReport = 0.0;
        
        // 3. Number of finished events (based on finishing time), and the average number of participants of these events.
        Integer numOfFinishedEvents = 0;
        Integer totalParticipants = 0;
        double averageParticipants = 0.0;
//        Integer numOfPaidEvents;
        
        // 4. Number of paid events finished (based on finishing time) and total revenue from these events
        Integer numOfFinishedPaidEvents = 0;
        Integer totalRevenue = 0;
        
        System.out.println("About to get organizer report for user id " + userid + " with start date as " + start_date1 + " and end date as "+ end_date1 + "...");
        
        try {
        	List<Event> events = eventRepo.listEventsForGivenUserAndTimeFrame(userid, end_date1, start_date1);
//        	List<Event> paidEvents = eventRepo.listPaidEventsForGivenUserAndTimeFrame(userid, end_date1, start_date1);
        	
        	
//        	for(Event e : events){
//                System.out.println(e.toString());
//            }
        	
        	if(events==null || events.size()==0){
                ErrorResponse error = new ErrorResponse("204", "No events");
                return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
            } 
//        	else {
            	numOfEventsCreated = events.size();
            	
            	for(int i=0; i<events.size(); i++){
            		
            		if(events.get(i).getFees()>0) {
            			totalNumberOfPaidEvents++;
            		}
            		
            		if(events.get(i).getStatus().equalsIgnoreCase("cancel")) {
            			numOfCancelledEvents++;
            			totalNumberOfCancelledEventsParticipants += participantRepo.findByEventID(events.get(i).getEventID()).size();
            			totalNumberOfCancelledEventsMinimumParticipants += events.get(i).getMinParticpants();
            			
            		}
            		
            		//if the event has ended before the current time/start_date
            		if(events.get(i).getEndtDate().isBefore(start_date)) {
            			if(events.get(i).getFees()>0) {
            				numOfFinishedPaidEvents++;
            				totalRevenue += events.get(i).getFees();
            			}
            			List<Participants> temp = participantRepo.findByEventID(events.get(i).getEventID());
            			var temp1 = temp.stream().filter((e) -> e.getStatus().equalsIgnoreCase("approved")).toArray();
            			totalParticipants += temp1.length;
            			numOfFinishedEvents++;
            		}
            	}
            	
            	paidEventsPercent = (double)totalNumberOfPaidEvents * 100 / numOfEventsCreated;
            	cancelledEventsFinalReport = (double)(totalNumberOfCancelledEventsParticipants) / totalNumberOfCancelledEventsMinimumParticipants;
            	averageParticipants = (double) (numOfFinishedEvents) / totalParticipants;
            	
//            }
        	
            	// requirement 1
            	response.put("NumberOfEventsCreated",numOfEventsCreated);
            	response.put("PercentageOfPaidEvents",paidEventsPercent);
            	
            	// requirement 2            	
            	response.put("NumOfCancelledEvents",numOfCancelledEvents);
            	response.put("TotalParticipantsToMinimumParticipantsRatio",cancelledEventsFinalReport);
            	
            	// requirement 3            	
            	response.put("NumberOfFinishedEvents",numOfFinishedEvents);
            	response.put("AverageParticipantsInFinishedEvents",averageParticipants);
            	
            	// requirement 4            	
            	response.put("NumberOfFinishedPaidEvents",numOfFinishedPaidEvents);
            	response.put("TotalRevenueFinishedPaidEvents",totalRevenue);
        	
            	return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("There was an error in UserLevelReportServiceImpl.getUserOrganizerReport()");
			e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
//        return new ResponseEntity<>("hello", HttpStatus.OK);
    }
}
