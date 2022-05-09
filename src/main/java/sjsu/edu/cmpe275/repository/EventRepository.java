package sjsu.edu.cmpe275.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sjsu.edu.cmpe275.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
	
	List<Event> findbyCity(String name, String brand);

}
