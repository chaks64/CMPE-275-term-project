package sjsu.edu.cmpe275.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sjsu.edu.cmpe275.service.ReviewsService;
import sjsu.edu.cmpe275.service.impl.ReviewsServiceImpl;

@RestController
@RequestMapping("/review")
@CrossOrigin
public class ReviewController {
	
	@Autowired
	private ReviewsService reviewsService; 
	
    @PostMapping(value = "/post")
    public ResponseEntity<?> postReview(@RequestBody Map<String, Object> inputJson){
        return reviewsService.postReview(inputJson);
    }
    
    @GetMapping(value = "/show/{userid}/{userType}")
	public ResponseEntity<?> getParticipants(@PathVariable String userid, @PathVariable String userType){
		return reviewsService.showReviews(userid, userType);
	}
}
