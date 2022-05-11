package sjsu.edu.cmpe275.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sjsu.edu.cmpe275.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	public List<Event> findByDescriptionIgnoreCaseContainsOrTitleIgnoreCaseContains(String description, String title);

}
