package sjsu.edu.cmpe275.service.impl;

import java.util.Collections;
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


//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.client.json.jackson.*;
//import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

import static sjsu.edu.cmpe275.config.Config.*;

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
				mailMessage.setTo(createdUser.getEmail());
				mailMessage.setSubject("Complete Verification!");
				mailMessage.setFrom("shahchintan64@gmail.com");
				mailMessage.setText("To verify your account, please click here : "
						+ "http://"+IP_ADDRESS+":8080/user/confirm-account?token="
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

	@Override
	public ResponseEntity<?> loginUser(Map<String, Object> reqBody) {
		System.out.println("in login "+ reqBody);
		try {
			String email = (String) reqBody.get("email");
			User user = userRepo.findByEmail(email);
			if(user == null) {
				ErrorResponse error = new ErrorResponse("404", "Email does not exists");
				return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
			}
			
			String password = (String) reqBody.get("password");
			if(!user.getPassword().equals(password)) {
				ErrorResponse error = new ErrorResponse("403", "Wrong Password");
				return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
			}
//			if(!user.isVerified()) {
//				ErrorResponse error = new ErrorResponse("403", "User Not vierfied");
//				return new ResponseEntity<>(error, HttpStatus.OK);
//			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
//		return null;
	}

	@Override
	public ResponseEntity<?> googleSignon(Map<String, Object> reqBody) {
		
		System.out.println("Req.body" + reqBody);
//		final HttpTransport transport = new NetHttpTransport();
//		final JsonFactory jsonFactory = new GsonFactory();
        String token = (String) reqBody.get("token");
        String subId = (String) reqBody.get("subId");
        
        ApacheHttpTransport transport = new ApacheHttpTransport();
        JacksonFactory jsonFactory1 = new JacksonFactory();
        
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory1)
        		.setAudience(Collections.singleton("384093796098-o4v5sjmes3i4stdnj9kepq5ftrkno6t9.apps.googleusercontent.com"))
        		.build();
        
//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
//                // Specify the CLIENT_ID of the app that accesses the backend:
//                .setAudience(Collections.singletonList("384093796098-o4v5sjmes3i4stdnj9kepq5ftrkno6t9.apps.googleusercontent.com"))
//                // Or, if multiple clients access the backend:
//                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
//                .build();
		
		return null;
	}

}
