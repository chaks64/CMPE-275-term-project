package sjsu.edu.cmpe275.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sjsu.edu.cmpe275.model.Forum;
import sjsu.edu.cmpe275.model.User;
import sjsu.edu.cmpe275.model.Event;


import sjsu.edu.cmpe275.RequestModel.ErrorResponse;
import sjsu.edu.cmpe275.repository.ForumRepository;
import sjsu.edu.cmpe275.repository.UserRepository;
import sjsu.edu.cmpe275.repository.EventRepository;

import sjsu.edu.cmpe275.service.ForumService;


@Service
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ForumRepository forumRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EventRepository eventRepo;

    @Override
    public ResponseEntity<?> createMsg(Map<String, Object> reqBody){
        System.out.println("in create msg for Forum "+ reqBody);
        try {
            Forum forum = new Forum();
            forum.setMsg((String) reqBody.get("msg"));
            forum.setForumType((String) reqBody.get("forumType"));
            System.out.println("DATATYPE:"+reqBody.get("userid").getClass().getSimpleName());
            Long userid = Long.parseLong((String) reqBody.get("userid"));
            User user = userRepo.findByUserId(userid);
            if(user == null) {
                ErrorResponse errorResponse = new ErrorResponse("404", "User not found");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
            Set<User> set = new HashSet<>();
            set.add(user);
            forum.setUser(set);

//            Long eventid = Long.parseLong((String) reqBody.get("eventid"));
            int eventid1 = (int) reqBody.get("eventid");
            Long eventid = Long.valueOf(eventid1);
            Event event = eventRepo.findByEventID(eventid);
            if(event == null) {
                ErrorResponse errorResponse = new ErrorResponse("404", "Event not found");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
            Set<Event> eventset = new HashSet<>();
            eventset.add(event);
            forum.setEvent(eventset);

            Forum newEvent = forumRepo.save(forum);

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

    @Override
    public ResponseEntity<?> getMsgForParticipantForum(Long eventID, String forumType) {

            System.out.println("in my msgs request for participant forum "+eventID);
            try {
                Long eventid = Long.parseLong(String.valueOf(eventID));
                List<Forum> msgList = forumRepo.findByEventEventIDAndForumType(eventid,"ParticipantForum");
                System.out.println(msgList);
                if(msgList==null || msgList.size()==0) {
                    ErrorResponse error = new ErrorResponse("204", "No msgs in participant forum for this event");
                    return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(msgList, HttpStatus.OK);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }


    public ResponseEntity<?> getMsgForSignUpForum(Long eventID, String forumType) {

        System.out.println("in my msgs request "+eventID);
        try {
            Long eventid = Long.parseLong(String.valueOf(eventID));
            List<Forum> msgList = forumRepo.findByEventEventIDAndForumType(eventid,"SignupForum");
            System.out.println(msgList);
            if(msgList==null || msgList.size()==0) {
                ErrorResponse error = new ErrorResponse("204", "No msgs in sign up forum for this event");
                return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(msgList, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    }