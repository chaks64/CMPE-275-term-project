package sjsu.edu.cmpe275.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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
import sjsu.edu.cmpe275.service.impl.EmailSenderService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginSignup {
	
	@Autowired
	private LoginSignupService loginSignupService;
	
	@PostMapping(value = "/signup")
	public ResponseEntity<?> createUser(@RequestBody Map<String, Object> inputJson){
		return loginSignupService.createUser(inputJson);
	}
	
	
}
