package et.debran.debranauth.model;

import org.springframework.stereotype.Component;

@Component
public class DebranUserMapper {
	
	public User mapLoginUser(LoginDto request) {
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		return user;
	}
	
	public User mapSignupUser(SignupDto request) {
		User user = new User();
		user.setEmail(request.getEmail());
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		user.setName(request.getName());
		return user;
	}
}
