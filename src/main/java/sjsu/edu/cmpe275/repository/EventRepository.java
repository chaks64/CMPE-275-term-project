package sjsu.edu.cmpe275.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.google.api.client.util.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.Query;
import sjsu.edu.cmpe275.model.Event;

@EnableJpaRepositories
public interface EventRepository extends JpaRepository<Event, Long>{
//	public Event findByEventID(Long eventid);
  public Event findOneByEventID(Long eventid);
	public List<Event> findAllByUserUserId(Long userid);
	public List<Event> findByDescriptionIgnoreCaseContainsOrTitleIgnoreCaseContains(String description, String title);
//	@Query(value = "SELECT * FROM event where lower(description) like CONCAT('%', :location, '%')", nativeQuery = true)
//	public List<Event> myfunction(String location, String status, String startTime, String endtDate, String keyword, String organizer);

//	@Query(value = "select * from event where lower(city) like :location and lower(status) = :status and DATE_FORMAT(start_date, '%m/%d/%Y %H:%i:%s') >= DATE_FORMAT(:startTime,'%m/%d/%Y %H:%i:%s') and DATE_FORMAT(endt_date, '%m/%d/%Y %H:%i:%s') >= DATE_FORMAT(:endtDate,'%m/%d/%Y %H:%i:%s') and (lower(description) like :keyword or lower(title) like :keyword)", nativeQuery = true)
//	public List<Event> myfunction(String location, String status, String startTime, String endtDate, String keyword);
	@Query(value = "select * from event inner join user on event.user_id = user.user_id where lower(event.city) like :location and lower(event.status) like :status and DATE_FORMAT(event.start_date, '%m/%d/%Y %H:%i:%s') >= DATE_FORMAT(:startTime,'%m/%d/%Y %H:%i:%s') and DATE_FORMAT(event.endt_date, '%m/%d/%Y %H:%i:%s') <= DATE_FORMAT(:endtDate,'%m/%d/%Y %H:%i:%s') and (lower(event.description) like :keyword or lower(event.title) like :keyword) and lower(user.screen_name) like :organizer", nativeQuery = true)
	public List<Event> myfunction(String location, String status, String startTime, String endtDate, String keyword, String organizer);
//=======

    public Event findByEventID(Long eventID);

	public List<Event> findAllByEventID(Long eventID);

	public List<Event> findAll();

	@Query(value = "select * from event where lower(city) like :location and lower(status) = :status and start_date >= date_format(:startTime,'%Y-%m-%d')  and endt_date <= date_format(:endtDate,'%Y-%m-%d') and (description like :keyword or title like :keyword);", nativeQuery = true)
	public List<Event> myfunction(String location, String status, String startTime, String endtDate, String keyword);



//	@Query(value = "select * from event where creation_time = :sysdate",nativeQuery = true)
//	public List<Event> listAllEventForGivenTimeFrame();

//	@Query(value = "select * from event where creation_time between :start_date and :end_date", nativeQuery = true)

//@Query(value = "select * from event where creation_time between :end_date1 and :start_date1", nativeQuery = true)
//public List<Event> listAllEventForGivenTimeFrame(String end_date1, String start_date1);

	//	@Query(value = "select * from event where creation_time = :sysdate",nativeQuery = true)
	//	public List<Event> listAllEventForGivenTimeFrame();
	
	//	@Query(value = "select * from event where creation_time between :start_date and :end_date", nativeQuery = true)
//	@Query(value = "select * from event where creation_time between :end_date1 and :start_date1", nativeQuery = true)
//	public List<Event> listAllEventForGivenTimeFrame(String end_date1, String start_date1);
//
	@Query(value = "select * from event where user_id = :userid and creation_time between :end_date1 and :start_date1", nativeQuery = true)
	public List<Event> listEventsForGivenUserAndTimeFrame(long userid, String end_date1, String start_date1);
	
//	@Query(value = "select * from event where user_id = :userid and creation_time between :end_date1 and :start_date1", nativeQuery = true)
//	public List<Event> listPaidEventsForGivenUserAndTimeFrame(long userid, String end_date1, String start_date1);



//=======

//    public Event findByEventID(Long eventID);

}
