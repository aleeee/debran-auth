package et.debran.debranauth.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import et.debran.debranauth.model.User;
import et.debran.debranauth.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	UserService userService;
	
	@Value("${debran.homepage}")
	private String homePage;
	
	@GetMapping("/debran-login")
	public String loadLoginPage() {
		return "login";
	}

	@PostMapping("/signup")
	@ResponseBody
	public String login(@ModelAttribute("signup") User user) {
		String token = userService.signup(user);
		return UriComponentsBuilder.fromUriString(homePage).queryParam("jwt_auth_token", token)
				.build().toUriString();
	}

}
