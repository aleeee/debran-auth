package et.debran.debranauth.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import et.debran.debranauth.model.Tutorial;
import et.debran.debranauth.service.TutorialService;

@CrossOrigin
@RestController
@RequestMapping("/tutorials")
public class TutorialController {
	
	@Autowired
	private TutorialService tutorialService;
	
	@GetMapping("/all")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) Optional<String> title){
		
		return	 new ResponseEntity<>(
				title.isPresent() ?
					tutorialService.searchTutorialByTitle(title.get())
					:tutorialService.findAll(),
					HttpStatus.OK);
					
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tutorial> getTutorialBId(@PathVariable("id") long id){
		return new ResponseEntity<>(
				 tutorialService.getTutorialById(id).get(),
				 HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<Tutorial>> getTutorialByPublisher(@RequestParam("published") boolean published){
		return new ResponseEntity<>(
				tutorialService.filterByPublished(published),
				HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Tutorial> postTutorial(@RequestBody Tutorial tutorial){
		return new ResponseEntity<>(tutorialService.createTutorial(tutorial),
				HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id,@RequestBody Tutorial tutorial){
		return new ResponseEntity<>(tutorialService.updateTutorial(tutorial),
				HttpStatus.CREATED);
	}
	@DeleteMapping("/{title}")
	public ResponseEntity<Tutorial> deleteTutorial(@PathVariable("title") String title){
		tutorialService.deleteTutorial(title);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
