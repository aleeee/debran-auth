package et.debran.debranauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import et.debran.debranauth.config.jwt.JwtAuthenticationEntryPoint;
import et.debran.debranauth.config.jwt.JwtAuthenticationFilter;
@Configuration
@EnableWebSecurity(debug=false)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
//	@Autowired
//	private OidcUserService oidcUserService;
//	@Autowired
//	private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authRepository;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthEntryPoint;
	
//	@Autowired
//	private DebranAuthenticationSuccessHandler authSuccessHandler;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	@Autowired
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(encoder);
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.cors()
			.and()
			.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("/h2-console/**")
			.permitAll()
			.antMatchers("/auth/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
//		http.authorizeRequests()
//			.antMatchers("/h2-console/**", "/auth/**", "/webjars/**").permitAll()
//			.anyRequest()
//			.authenticated()
//			.and()
//			.exceptionHandling()
//			.authenticationEntryPoint(jwtAuthEntryPoint)
//			.and()
//			.oauth2Login()
//			.loginPage("/auth/debran-login")
//			.redirectionEndpoint()
////			.baseUri("/oauth2/authorize/google")
//			.and()
//			.userInfoEndpoint()
//			.oidcUserService(oidcUserService)
//			.and()
//			.authorizationEndpoint()
//			.baseUri("/oauth2/authorize")
//			.authorizationRequestRepository(authRepository)
//			.and()
//			.successHandler(authSuccessHandler);
//			
////	       http.csrf().disable();
////	        http.headers().frameOptions().disable();
//	        
	        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	
}
