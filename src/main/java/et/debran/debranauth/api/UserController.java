package et.debran.debranauth.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import et.debran.debranauth.model.User;
import et.debran.debranauth.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping
    public ResponseEntity<?> saveUser (@RequestBody User user){
    	return 
    			ResponseEntity.ok().build();
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
    	return ResponseEntity.ok(userService.getAllUsers());
    }
}
