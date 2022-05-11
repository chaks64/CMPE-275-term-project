package sjsu.edu.cmpe275.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import sjsu.edu.cmpe275.model.Event;

@EnableJpaRepositories
public interface EventRepository extends JpaRepository<Event, Long>{
	public Event findByEventID(Long eventid);
	public List<Event> findAllByUserUserId(Long userid);
}
