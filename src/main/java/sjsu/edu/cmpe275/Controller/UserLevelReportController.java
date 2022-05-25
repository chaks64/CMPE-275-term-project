package sjsu.edu.cmpe275.Controller;

import java.time.LocalDateTime;
import java.util.*;

import com.google.api.client.util.DateTime;
import net.minidev.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import sjsu.edu.cmpe275.service.EventService;
import sjsu.edu.cmpe275.service.UserLevelReportService;

@Controller
@RequestMapping("/user")
@CrossOrigin

public class UserLevelReportController {

    @Autowired
    private UserLevelReportService userLevelReportService;

    @GetMapping(value = "/participationReport/{userid}")
    public ResponseEntity<?> getUserParticipationReport(@PathVariable Long userid){
        return userLevelReportService.getUserParticipationReport(userid);
    }


}
