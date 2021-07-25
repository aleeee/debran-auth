package et.debran.debranauth.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import et.debran.debranauth.exception.DebranException;
import et.debran.debranauth.model.User;
import et.debran.debranauth.repo.UserRepository;
import et.debran.debranauth.util.JwtTokenUtil;

@Service
public class DebranUserService implements UserService {
	private static final Logger log = LoggerFactory.getLogger(DebranUserService.class);

	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	@Autowired
	BCryptPasswordEncoder pwdEncoder;

	@Override
	public User save(User user) {
		log.info("save user {}", user);
		Optional<User> dbUser = userRepository.findByEmail(user.getEmail());
		if (dbUser.isPresent()) {
			throw new RuntimeException("User already exist.");
		}
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		log.info("get all users");
		return userRepository.findAll();
	}

	@Override
	public void delete(long id) {
		log.info("delete user by id {}", id);
		userRepository.deleteById(id);
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		log.info("get user by username {}", username);
		return userRepository.findByUsername(username);
	}
	@Override
	public Optional<User> getUserByEmail(String email) {
		log.info("get user by email {}", email);
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<User> getUserById(long id) {
		log.info("get user by id {}", id);
		return userRepository.findById(id);
	}

	@Override
	public User update(User userData) throws DebranException {
		User user = getUserById(userData.getId()).orElseThrow(
				() -> new DebranException("user " + userData.getEmail() + " not found", HttpStatus.NOT_FOUND));

		BeanUtils.copyProperties(userData, user, "password");
		return userRepository.save(user);
	}

}
