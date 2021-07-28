package et.debran.debranauth.service;

import java.util.List;
import java.util.Optional;

import et.debran.debranauth.model.Tutorial;

public interface TutorialService {
	List<Tutorial> findAll();
	List<Tutorial> filterByPublished(boolean isPublished);
	List<Tutorial> searchTutorialByTitle(String title);
	Optional<Tutorial> getTutorialByTitle(String title);
	Optional<Tutorial> getTutorialById(long id);
	Tutorial createTutorial(Tutorial tutorial);
	void deleteTutorial(String title);
	Tutorial updateTutorial(Tutorial turorial);
}
