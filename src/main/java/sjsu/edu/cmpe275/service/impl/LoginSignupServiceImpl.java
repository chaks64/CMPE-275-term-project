package sjsu.edu.cmpe275.service.impl;

import java.util.Collections;
import java.util.List;
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

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

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
			newUser.setGoogleSubId("-1");
			
			Map<String, String> addressMap =(Map<String, String>) reqBody.get("address");
			
			Address address = new Address();
			address.setCity(addressMap.get("street"));
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
			String password = (String) reqBody.get("password");
			User user = userRepo.findByEmail(email);
			if(user == null || password.equals("-1")) {
				ErrorResponse error = new ErrorResponse("404", "Email does not exists");
				return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
			}
			if(!user.getPassword().equals(password)) {
				ErrorResponse error = new ErrorResponse("403", "Wrong Password");
				return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
			}
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
        
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
        		.setAudience(Collections.singletonList("155505368995-kkg9c40j38vb7c8lg05m2jke4rmtia4l.apps.googleusercontent.com"))
//        		.setIssuer("accounts.google.com")
        		.build();
        System.out.println(verifier);
        
        try {
        	
			GoogleIdToken idToken = verifier.verify(token);
			System.out.println("here for check "+token);
			if (idToken != null) {
                Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println("User ID: " + payload);
//                
//
//                // Get profile information from payload
//                String email = payload.getEmail();
//                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//                String name = (String) payload.get("name");
//                String pictureUrl = (String) payload.get("picture");
//                String locale = (String) payload.get("locale");
//                String familyName = (String) payload.get("family_name");
//                String givenName = (String) payload.get("given_name");
//
//                System.out.println("email:"+email);
//                System.out.println("givenName:"+name);
//
                // New or Existing user Logic
                List<User> userList = userRepo.findAll();
                System.out.println(userList.size());
                for(User user : userList) {
                	if(user.getGoogleSubId().equals(subId)) {
                		return new ResponseEntity<>(user, HttpStatus.OK);
                	}
                }
            }
			
		} catch(Exception error){
       	 System.out.println(error);
       	 return new ResponseEntity<>("User Not Found", HttpStatus.BAD_REQUEST);             
       }
		
        return new ResponseEntity<>("Newuser", HttpStatus.PARTIAL_CONTENT);
	}

	@Override
	public ResponseEntity<?> googleSignup(Map<String, Object> reqBody) {
		System.out.println("google signup "+ reqBody);
		try {
			String email = (String) reqBody.get("email");
			User user = userRepo.findByEmail(email);
			if(user != null) {
				ErrorResponse error = new ErrorResponse("409", "Email Already exists");
				return new ResponseEntity<>(error, HttpStatus.CONFLICT);
			}
			
			User newUser = new User();
			newUser.setEmail((String) reqBody.get("email"));
			newUser.setPassword("-1");
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
			
			String tokenId = (String) reqBody.get("token");
	        String subId = (String) reqBody.get("subId");
	        newUser.setGoogleSubId(subId);
	        
	        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
	        		.setAudience(Collections.singleton("155505368995-kkg9c40j38vb7c8lg05m2jke4rmtia4l.apps.googleusercontent.com"))
	        		.build();
			
			try {
				GoogleIdToken idToken = verifier.verify(tokenId);
				System.out.println(tokenId);
				if (idToken != null) {
	                Payload payload = idToken.getPayload();

	                // Print user identifier
	                String userId = payload.getSubject();
	                System.out.println("User ID: " + userId);
	                newUser.setFullName((String) payload.get("name"));
	                newUser.setEmail(payload.getEmail());
	                User createdUser = userRepo.save(newUser);
	    			if(createdUser == null) {
	    				ErrorResponse errorResponse = new ErrorResponse("E02", "Unable to create patient/admin");
	    				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    			} else {
	    				return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	    			}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}

}
