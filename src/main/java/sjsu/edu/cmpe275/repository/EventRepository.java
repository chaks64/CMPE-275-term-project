package sjsu.edu.cmpe275.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sjsu.edu.cmpe275.model.Event;

@EnableJpaRepositories
public interface EventRepository extends JpaRepository<Event, Long>{
	public Event findByEventID(Long eventid);
  public Event findOneByEventID(Long eventid);
	public List<Event> findAllByUserUserId(Long userid);
	public List<Event> findByDescriptionIgnoreCaseContainsOrTitleIgnoreCaseContains(String description, String title);
//	@Query(value = "SELECT * FROM event where lower(description) like CONCAT('%', :location, '%')", nativeQuery = true)
//	public List<Event> myfunction(String location, String status, String startTime, String endtDate, String keyword, String organizer);
	@Query(value = "select * from event where lower(city) like :location and lower(status) = :status and start_date like :startTime and endt_date like :endtDate and (description like :keyword or title like :keyword);", nativeQuery = true)
	public List<Event> myfunction(String location, String status, String startTime, String endtDate, String keyword);
}
