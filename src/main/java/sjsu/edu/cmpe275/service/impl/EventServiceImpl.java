package sjsu.edu.cmpe275.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import sjsu.edu.cmpe275.repository.EventRepository;
import sjsu.edu.cmpe275.service.EventService;

public class EventServiceImpl implements EventService{
	
	@Autowired
	private EventRepository eventRepo;

	@Override
	public ResponseEntity<?> listEvents() {
		// TODO Auto-generated method stub
		
		return null;
	}

}
