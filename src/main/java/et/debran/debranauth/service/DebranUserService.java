package et.debran.debranauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import et.debran.debranauth.model.User;
import et.debran.debranauth.repo.UserRepository;
import et.debran.debranauth.util.JwtTokenUtil;

@Service
public class DebranUserService implements UserService{
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	@Autowired
	BCryptPasswordEncoder pwdEncoder;
	
	@Override
	public String signup(User user) {
		
	        User dbUser = userRepository.findByEmail(user.getEmail());
	        if (dbUser != null) {
	            throw new RuntimeException("User already exist.");
	        }
	        user.setPassword(pwdEncoder.encode(user.getPassword()));
	        userRepository.save(user);
	        return jwtTokenUtil.createJwtToken(user.getEmail());
	    }
	

}
