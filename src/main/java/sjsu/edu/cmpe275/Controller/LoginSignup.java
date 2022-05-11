package sjsu.edu.cmpe275.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import sjsu.edu.cmpe275.model.ConfirmationToken;
import sjsu.edu.cmpe275.model.User;
import sjsu.edu.cmpe275.repository.ConfirmTokenRepository;
import sjsu.edu.cmpe275.repository.UserRepository;
import sjsu.edu.cmpe275.service.LoginSignupService;

@Controller
@RequestMapping("/user")
@CrossOrigin
public class LoginSignup {
	
	@Autowired
	private LoginSignupService loginSignupService;
	
	@Autowired
	private ConfirmTokenRepository confirmTokenRepository;
	
	@Autowired
	private UserRepository userRepo;
	
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> logineUser(@RequestBody Map<String, Object> inputJson){
		return loginSignupService.loginUser(inputJson);
	}
	
	@PostMapping(value = "/signup")
	public ResponseEntity<?> createUser(@RequestBody Map<String, Object> inputJson){
		System.out.println("register here");
		return loginSignupService.createUser(inputJson);
	}
	
	
	@RequestMapping(value="/googlesignon", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<?> googleSignon(@RequestBody Map<String, Object> inputJson){
		System.out.println("here");
        return loginSignupService.googleSignon(inputJson);
    }
	
	@RequestMapping(value="/googlesignup", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<?> googleSignup(@RequestBody Map<String, Object> inputJson){
		System.out.println("here");
        return loginSignupService.googleSignup(inputJson);
    }
	
	@RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		//System.out.println("inside method to confirm");
		ConfirmationToken token = confirmTokenRepository.findByConfirmationToken(confirmationToken);
		HttpHeaders headers = new HttpHeaders();
		if (token != null) {
			User user = userRepo.findByEmail(token.getUser().getEmail());
			user.setVerified(true);
			User newUser = userRepo.save(user);
			System.out.println("confirmed " + newUser.getFullName() + "verification " + newUser.isVerified());
			
		    headers.add("Location", "http://localhost:3000/verify");    
		    return new ResponseEntity<Object>(headers, HttpStatus.FOUND);
		} else {
			System.out.println("here error link");
			headers.add("Location", "http://localhost:3000/error");    
		    return new ResponseEntity<Object>(headers, HttpStatus.FOUND);
		}
	}
}
