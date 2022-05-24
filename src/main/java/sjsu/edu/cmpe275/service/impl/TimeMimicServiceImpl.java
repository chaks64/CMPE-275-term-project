package sjsu.edu.cmpe275.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sjsu.edu.cmpe275.RequestModel.ErrorResponse;
import sjsu.edu.cmpe275.model.Event;
import sjsu.edu.cmpe275.model.VirtualTime;

@Service
public class TimeMimicServiceImpl {
	public ResponseEntity<?> updateTime(Map<String, Object> reqBody){
		System.out.println("in update time "+ reqBody);
		try {
			VirtualTime vTime = VirtualTime.getInstance();
			LocalDateTime vmTime = LocalDateTime.parse((CharSequence) reqBody.get("time"));
			vTime.setSystemTime(vmTime);
			return new ResponseEntity<>(vmTime, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<?> getTime() {
		try {
			VirtualTime vTime = VirtualTime.getInstance();
			System.out.println("---------"+vTime.getSystemTime());
			Map<String, LocalDateTime> timeMap = new HashMap<>();
			timeMap.put("Time", vTime.getSystemTime());
			return new ResponseEntity<>(timeMap, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("500", "Server Error");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
