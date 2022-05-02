package sjsu.edu.cmpe275.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sjsu.edu.cmpe275.RequestModel.ErrorResponse;
import sjsu.edu.cmpe275.RequestModel.SignupRequest;
import sjsu.edu.cmpe275.model.ConfirmationToken;
import sjsu.edu.cmpe275.model.User;
import sjsu.edu.cmpe275.repository.ConfirmTokenRepository;
import sjsu.edu.cmpe275.service.LoginSignupService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginSignup {
	
	@Autowired
	private LoginSignupService loginSignupService;
	
	@Autowired
	private ConfirmTokenRepository confirmTokenRepo;
	
	@PostMapping(value = "/signup")
	public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest){
		try {
			User user = new User();
			user.setEmail(signupRequest.getEmail());
			user.setAccountType(signupRequest.getAccountType());
			user.setFullName(signupRequest.getFullName());
			user.setGender(signupRequest.getGender());
			user.setAddress(signupRequest.getAddress());
			user.setDescription(signupRequest.getDescription());
			user.setScreenName(signupRequest.getScreenName());
			user.setVerified(false);
			User newUser = loginSignupService.createUser(user);
			
			if(newUser == null) {
				ErrorResponse errorResponse = new ErrorResponse("E02", "Unable to create patient/admin");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				ConfirmationToken token = new ConfirmationToken(newUser);
				confirmTokenRepo.save(token);
//				SendM
			}
			
		} catch (Exception e) {
			
		}
		return null;
	}
}
