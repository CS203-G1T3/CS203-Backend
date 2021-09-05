package CovidLoveit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CovidLoveitStartup {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(CovidLoveitStartup.class, args);
	}

}
