package et.debran.debranauth.constants;

public class DebranConstants {
	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;
	public static final String SIGNING_KEY = "debran2021";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String AUTH_HEADER = "Authorization";
	public static final String TOKEN_PARAM = "jwt_auth_token";
}
