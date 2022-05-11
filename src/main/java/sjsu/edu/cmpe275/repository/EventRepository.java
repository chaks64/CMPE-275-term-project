package sjsu.edu.cmpe275.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sjsu.edu.cmpe275.model.Event;


public interface EventRepository extends JpaRepository<Event, Long>{
	public Event findByEventID(Long eventid);
public List<Event> findByDescriptionIgnoreCaseContainsOrTitleIgnoreCaseContains(String description, String title);
}
