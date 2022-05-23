package sjsu.edu.cmpe275.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sjsu.edu.cmpe275.model.Reviews;

public interface ReviewRepository extends JpaRepository<Reviews, Long>{

}
