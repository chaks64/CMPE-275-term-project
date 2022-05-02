package sjsu.edu.cmpe275.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjsu.edu.cmpe275.model.User;
import sjsu.edu.cmpe275.repository.UserRepository;
import sjsu.edu.cmpe275.service.LoginSignupService;

@Service
public class LoginSignupServiceImpl implements LoginSignupService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public User createUser(User user) {
		return userRepo.save(user);
	}

}
