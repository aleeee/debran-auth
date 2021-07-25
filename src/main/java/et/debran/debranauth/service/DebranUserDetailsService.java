package et.debran.debranauth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import et.debran.debranauth.model.DebranRole;
import et.debran.debranauth.model.User;
import et.debran.debranauth.repo.UserRepository;

@Service
public class DebranUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("invalid username or password"));
		List<DebranRole> r = user.getRoles();
		List<SimpleGrantedAuthority> roles = user.getRoles()
		.stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
		.collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),roles); 
        			
	}

}
