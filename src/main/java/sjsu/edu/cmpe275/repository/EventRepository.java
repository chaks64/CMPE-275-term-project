package sjsu.edu.cmpe275.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sjsu.edu.cmpe275.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

}
