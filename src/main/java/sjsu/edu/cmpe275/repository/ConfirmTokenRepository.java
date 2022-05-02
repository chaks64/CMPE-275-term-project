package sjsu.edu.cmpe275.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sjsu.edu.cmpe275.model.ConfirmationToken;

public interface ConfirmTokenRepository extends JpaRepository<ConfirmationToken, Long>{
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
