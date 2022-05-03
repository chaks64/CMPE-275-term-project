package sjsu.edu.cmpe275.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sjsu.edu.cmpe275.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findByEmail(String email);
}
