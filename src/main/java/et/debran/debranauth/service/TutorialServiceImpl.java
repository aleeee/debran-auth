package et.debran.debranauth.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import et.debran.debranauth.model.Tutorial;
import et.debran.debranauth.repo.TutorialRepository;

@Service
public class TutorialServiceImpl implements TutorialService{
	private static final Logger log = LoggerFactory.getLogger(TutorialServiceImpl.class);
	
	@Autowired
	TutorialRepository tutorialRepository;
	
	@Override
	public List<Tutorial> findAll() {
		log.info("find all tutorials");
		return tutorialRepository.findAll();
	}

	@Override
	public List<Tutorial> filterByPublished(boolean isPublished) {
		log.info("filter tutorials by published {} ",isPublished);
		return tutorialRepository.findByPublished(isPublished);
	}

	
	@Override
	public Optional<Tutorial> getTutorialById(long id) {
		log.info("filter tutorials by id {} ",id);
		return tutorialRepository.findById(id);
	}

	@Override
	public List<Tutorial> searchTutorialByTitle(String title) {
		log.info("search tutorial by title {}", title);
		return tutorialRepository.searchByTitle(title);
	}

	@Override
	public Optional<Tutorial> getTutorialByTitle(String title) {
		log.info("get tutorial by title {}", title);
		return tutorialRepository.findByTitle(title);
	}

	@Override
	public Tutorial createTutorial(Tutorial tutorial) {
		log.info("create tutorial {}", tutorial);
		return tutorialRepository.save(tutorial);
	}

	@Override
	public void deleteTutorial(String title) {
		log.info("delete tutorial by title {}", title);
		Optional<Tutorial> tutorial = tutorialRepository.findByTitle(title);
		tutorial.ifPresent( t -> tutorialRepository.delete(t));
	}

	@Override
	public Tutorial updateTutorial(Tutorial tut) {
		log.info("update tutorial  {}", tut);
		Optional<Tutorial> tutorial = tutorialRepository.findById(tut.getId());
		tutorial.ifPresent( t -> {
				t.setTitle(tut.getTitle());
				t.setDescription(tut.getDescription());
				t.setPublished(tut.isPublished());
				tutorialRepository.save(t);
				}
				);
		return tutorialRepository.getById(tut.getId());
	}

}
