package et.debran.debranauth.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import et.debran.debranauth.model.User;
import et.debran.debranauth.model.UserType;
import et.debran.debranauth.repo.UserRepository;

@Service
public class DebranOidcUserService extends OidcUserService {
	private static final Logger log = LoggerFactory.getLogger(DebranOidcUserService.class);
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) {
		
		OidcUser oidcUser = super.loadUser(userRequest);
		Map<String, Object>  attributes = oidcUser.getAttributes();
		User gUserInfo = new User();
		gUserInfo.setName((String) attributes.get("name"));
		gUserInfo.setSub((String)attributes.get("sub"));
		gUserInfo.setEmail((String)attributes.get("email"));		
		gUserInfo.setImageUrl((String)attributes.get("picture"));
		log.info("received user request {} " , gUserInfo);
		updateUser(gUserInfo);
		return oidcUser;
	}
	
	private void updateUser(User userInfo) {
		User user = userRepository.findByEmail(userInfo.getEmail()).orElse(new User());
		user.setEmail(userInfo.getEmail());
		user.setImageUrl(userInfo.getImageUrl());
		user.setName(userInfo.getName());
		user.setUserType(UserType.GOOGLE);
		
		userRepository.save(user);
	}
}
