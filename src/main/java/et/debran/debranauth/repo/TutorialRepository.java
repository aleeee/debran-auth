package et.debran.debranauth.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import et.debran.debranauth.model.Tutorial;
@Repository
public interface TutorialRepository extends JpaRepository<Tutorial	, Long>{
	
	List<Tutorial> findByPublished(boolean published);
	List<Tutorial> searchByTitle(String title);
	Optional<Tutorial> findByTitle(String title);
}
