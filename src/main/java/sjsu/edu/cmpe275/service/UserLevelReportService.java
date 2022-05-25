package sjsu.edu.cmpe275.service;

import java.time.LocalDateTime;
import java.util.Map;

import com.google.api.client.util.DateTime;
import org.springframework.http.ResponseEntity;

public interface UserLevelReportService {
    ResponseEntity<?> getUserParticipationReport(Long userid);
    ResponseEntity<?> getUserOrganizerReport(Long userid);
}
