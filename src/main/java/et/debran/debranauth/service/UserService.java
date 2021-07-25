package et.debran.debranauth.service;

import java.util.List;
import java.util.Optional;

import et.debran.debranauth.exception.DebranException;
import et.debran.debranauth.model.User;

public interface UserService {
	
	User save(User user);
	List<User> getAllUsers();
	void delete(long id);
	Optional<User> getUserByUsername(String username);
	Optional<User> getUserByEmail(String email);
	Optional<User> getUserById(long id);
	User update(User user) throws DebranException;
}
