package sjsu.edu.cmpe275.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import sjsu.edu.cmpe275.RequestModel.ErrorResponse;
import sjsu.edu.cmpe275.model.Address;
import sjsu.edu.cmpe275.model.ConfirmationToken;
import sjsu.edu.cmpe275.model.User;
import sjsu.edu.cmpe275.repository.ConfirmTokenRepository;
import sjsu.edu.cmpe275.repository.UserRepository;
import sjsu.edu.cmpe275.service.LoginSignupService;

@Service
public class LoginSignupServiceImpl implements LoginSignupService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ConfirmTokenRepository confirmTokenRepo;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Override
	public ResponseEntity<?> createUser(Map<String, Object> reqBody) {
//		return userRepo.save(user);
		System.out.println("in signup "+ reqBody);
		
		
		try {
			String email = (String) reqBody.get("email");
			User user = userRepo.findByEmail(email);
			if(user != null) {
				ErrorResponse error = new ErrorResponse("409", "Email Already exists");
				return new ResponseEntity<>(error, HttpStatus.CONFLICT);
			}
			
			User newUser = new User();
			newUser.setEmail((String) reqBody.get("email"));
			newUser.setPassword((String) reqBody.get("password"));
			newUser.setAccountType((String) reqBody.get("accountType"));
			newUser.setDescription((String) reqBody.get("description"));
			newUser.setFullName((String) reqBody.get("fullName"));
			newUser.setGender((String) reqBody.get("gender"));
			newUser.setScreenName((String) reqBody.get("screenName"));
			newUser.setVerified(false);
			
			Map<String, String> addressMap =(Map<String, String>) reqBody.get("address");
			
			Address address = new Address();
			address.setCity(addressMap.get("city"));
			address.setNumber(addressMap.get("number"));
			address.setState(addressMap.get("state"));
			address.setZipCode(addressMap.get("zipcode"));
			
			newUser.setAddress(address);
			
			User createdUser = userRepo.save(newUser);
			if(createdUser == null) {
				ErrorResponse errorResponse = new ErrorResponse("E02", "Unable to create patient/admin");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				ConfirmationToken token = new ConfirmationToken(newUser);
				System.out.println("here for verfication");
				confirmTokenRepo.save(token);
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo("c.shah12345@gmail.com");
				mailMessage.setSubject("Complete Verification!");
				mailMessage.setFrom("shahchintan64@gmail.com");
				mailMessage.setText("To verify your account, please click here : "
						+ "http://localhost:8080/user/confirm-account?token="
						+ token.getConfirmationToken());
				emailSenderService.sendEmail(mailMessage);
				return new ResponseEntity<>(newUser, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
