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
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime start_date =  LocalDateTime.now();
        String start_date1 = start_date.format(outputFormatter);
        System.out.println("start_date: "+start_date1);
        LocalDateTime end_date = LocalDateTime.now().minusDays(90);
        String end_date1 = end_date.format(outputFormatter);
        System.out.println("end_date: "+end_date1);
        System.out.println("user id: "+userid);


        try {
            List<Participants> participants = participantRepo.listAllEventsForUserInGivenTimeFrame(userid,end_date1,start_date1);

            for(Participants p : participants){
                System.out.println(p.toString());
            }

//            System.out.println("hello "+participants.get(0).toString());
//            System.out.println("printing sign up time :" +participants.get(0).getSignUpTime().toString());

            int noOfSignedUpEvents = 0;
            int noOfRejectsAndApprovals = 0;
            Long eventid = null;
            List<Event> noOfFinishedEvents = new ArrayList<>();

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


                }
//                noOfSignedUpEvents = participants.size();

                System.out.println("No of signed up events: "+noOfSignedUpEvents);
                System.out.println("No of rejects and approvals: "+noOfRejectsAndApprovals);
                System.out.println("No of finished events: "+noOfFinishedEvents);
                return new ResponseEntity<>(participants, HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
