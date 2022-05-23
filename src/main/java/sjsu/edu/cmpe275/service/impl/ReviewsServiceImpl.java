package sjsu.edu.cmpe275.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import sjsu.edu.cmpe275.RequestModel.ErrorResponse;
import sjsu.edu.cmpe275.model.Forum;
import sjsu.edu.cmpe275.model.Reviews;
import sjsu.edu.cmpe275.model.User;
import sjsu.edu.cmpe275.repository.ReviewRepository;
import sjsu.edu.cmpe275.repository.UserRepository;
import sjsu.edu.cmpe275.service.ReviewsService;

@Service
public class ReviewsServiceImpl implements ReviewsService{
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Override
	public ResponseEntity<?> postReview(Map<String, Object> reqBody) {
		System.out.println("in post review "+ reqBody);
		try {
			Integer integer = (Integer) reqBody.get("userid");
			Long userid = new Long(integer);
			User user = userRepository.findByUserId(userid);
			if(user == null) {
				ErrorResponse errorResponse = new ErrorResponse("E02", "User not found");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			Reviews reviews = new Reviews();
			reviews.setRating((int) reqBody.get("rating"));
			reviews.setReview((String) reqBody.get("review"));
			reviews.setReviewType((String) reqBody.get("type"));
			reviews.setUser(user);
			Reviews newReview = reviewRepository.save(reviews);
			if(newReview == null) {
				ErrorResponse errorResponse = new ErrorResponse("E02", "Unable to create event");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			Set<Reviews> set = user.getReviews();
			set.add(newReview);
			User newUser = userRepository.save(user);
			if(newUser == null) {
				ErrorResponse errorResponse = new ErrorResponse("E02", "Unable to create event");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("New Event Created!");
			mailMessage.setFrom("shahchintan64@gmail.com");
			mailMessage.setText("You have been reviewed");
			emailSenderService.sendEmail(mailMessage);
			
			return new ResponseEntity<>(newReview , HttpStatus.OK);
	
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@Override
	public ResponseEntity<?> showReviews(String userId) {
		System.out.println("in show review "+userId);
        try {
            Long userid = Long.parseLong(String.valueOf(userId));
            User user = userRepository.findByUserId(userid);
            if(user==null) {
                ErrorResponse error = new ErrorResponse("404", "User Not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            } else {
            	Set<Reviews> reviews = user.getReviews();
            	System.out.println(reviews);
//            	Set<Reviews> reviews1 = reviews.stream()
//            			.filter(r -> r.getRating()==4)
//            			.collect(Collectors.toSet());
            	
                return new ResponseEntity<>(reviews, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
}
