package et.debran.debranauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import et.debran.debranauth.config.SecurityConfig;

@SpringBootApplication
public class DebranAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebranAuthApplication.class, args);
	}

}
