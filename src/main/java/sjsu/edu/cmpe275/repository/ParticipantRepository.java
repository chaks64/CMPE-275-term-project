package sjsu.edu.cmpe275.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import sjsu.edu.cmpe275.model.Event;
import sjsu.edu.cmpe275.model.Participants;

public interface ParticipantRepository extends JpaRepository<Participants, Long>{
	public List<Participants> findByUserIdAndStatus(Long userid, String status);
	
	public List<Participants> findByEventIDAndStatus(Long eventid, String status);
	
	public List<Participants> findByEventID(Long eventid);

	public List<Participants> findAllByEventID(Long eventid);
	
	public List<Participants> findAllByUserId(Long userId);
	
	public Participants findByUserId(Long userid);
	
	public Participants findByUserIdAndEventID(Long userid, Long eventid);

	@Query(value = "select * from participants where user_id = :userid and sign_up_time between :end_date1 and :start_date1", nativeQuery = true)
	public List<Participants> listAllEventsForUserInGivenTimeFrame(Long userid, String end_date1, String start_date1);
}