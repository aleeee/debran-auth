package et.debran.debranauth.config;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import et.debran.debranauth.model.User;
import et.debran.debranauth.repo.UserRepository;
import et.debran.debranauth.util.JwtTokenUtil;
import static et.debran.debranauth.constants.DebranConstants.TOKEN_PARAM;

@Component
public class DebranAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Value("${debran.homepage}")
	private String homePage;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		if(response.isCommitted())return;
		
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		Map<String, Object> attributes = oidcUser.getAttributes();
		String email = (String) attributes.get("email");
		User user = userRepository.findByEmail(email).orElseThrow();
		String token = jwtTokenUtil.createJwtToken(user.getEmail());
		String redirectionUrl = UriComponentsBuilder
				.fromUriString(homePage)
				.queryParam(TOKEN_PARAM,token)
				.build().toUriString();
		getRedirectStrategy()
			.sendRedirect(request, response, redirectionUrl);
	}
	
	
}
