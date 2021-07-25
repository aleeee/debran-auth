package et.debran.debranauth.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import et.debran.debranauth.exception.DebranException;
import et.debran.debranauth.model.DebranAuthToken;
import et.debran.debranauth.model.DebranRole;
import et.debran.debranauth.model.DebranUserMapper;
import et.debran.debranauth.model.DebranUserRole;
import et.debran.debranauth.model.LoginDto;
import et.debran.debranauth.model.SignupDto;
import et.debran.debranauth.model.User;
import et.debran.debranauth.service.UserService;
import et.debran.debranauth.util.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	DebranUserMapper mapper;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignupDto request) {

		if (userService.getUserByUsername(request.getUsername()).isPresent()) {
			log.error("Error username {} alreay taken ", request.getUsername());
			return ResponseEntity.badRequest().body(new DebranException(
					"username " + request.getUsername() + " already taken", HttpStatus.BAD_REQUEST));
		}
		if (userService.getUserByEmail(request.getEmail()).isPresent()) {
			log.error("Error email {} is already associated with an account ", request.getEmail());
			return ResponseEntity.badRequest().body(new DebranException(
					"email " + request.getEmail() + " is already associated with an account", HttpStatus.BAD_REQUEST));
		}

		User user = mapper.mapSignupUser(request);
		List<DebranRole> roles = new ArrayList<>();
		roles.add(new DebranRole(DebranUserRole.ROLE_USER));
		user.setRoles(roles);

		userService.save(user);

		return new ResponseEntity<>("Signup Success", HttpStatus.CREATED);

	}

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@Valid @RequestBody LoginDto user) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtToken = jwtTokenUtil.createJwtToken(user.getUsername());

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(r -> r.getAuthority())
				.collect(Collectors.toList());

		DebranAuthToken token = new DebranAuthToken();

		token.setJwtToken(jwtToken);
		token.setRoles(roles);
		token.setUserName(userDetails.getUsername());
		
		return new ResponseEntity<>(token,HttpStatus.CREATED);
	}


}
