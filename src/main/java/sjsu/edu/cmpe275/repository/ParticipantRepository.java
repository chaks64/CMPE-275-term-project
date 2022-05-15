package sjsu.edu.cmpe275.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sjsu.edu.cmpe275.model.Participants;

public interface ParticipantRepository extends JpaRepository<Participants, Long>{
	public List<Participants> findByUserIdAndStatus(Long userid, String status);
	
	public List<Participants> findByEventIDAndStatus(Long eventid, String status);
	
	public List<Participants> findByEventID(Long eventid);
	
	public List<Participants> findAllByUserId(Long userId);
	
	public Participants findByUserId(Long userid);
}