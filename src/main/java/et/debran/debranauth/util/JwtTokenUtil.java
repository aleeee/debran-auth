package et.debran.debranauth.util;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static et.debran.debranauth.constants.DebranConstants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static et.debran.debranauth.constants.DebranConstants.SIGNING_KEY;
@Component
public class JwtTokenUtil {
	
	@Value("${debran.jwt.issuer}")
	private String jwtIssuer;
	
	public String createJwtToken(String subject) {
		Claims claims = Jwts.claims().setSubject(subject);
		claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuer(jwtIssuer)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
				.signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
				.compact();
	}
	
	
	public boolean validateJwtToken(String token, UserDetails userDetails) {
		final String username = getUserNameFromToken(token);
		return username.equals(userDetails.getUsername()) 
				&& !isTokenExpired(token);
	}

	


	public String getUserNameFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.getSubject();
	}
	
	private Claims getClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(SIGNING_KEY)
				.parseClaimsJws(token)
				.getBody();
	}
	
	private boolean isTokenExpired(String token) {
		Date expirationDate = getClaimsFromToken(token).getExpiration();
		return expirationDate.before(new Date());
	}
}
