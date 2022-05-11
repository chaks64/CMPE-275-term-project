package sjsu.edu.cmpe275.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sjsu.edu.cmpe275.model.Forum;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long>{
//        public List<Forum> findAllByEventEventID(Long eventID);

        public List<Forum> findByEventEventIDAndForumType (Long eventid, String forumType);


}
