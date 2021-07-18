package et.debran.debranauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;


@Configuration
public class AuthBeanConfig {
	
	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> debranAuthorizationReqRepository(){
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}
	
	
	@Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
	
}
