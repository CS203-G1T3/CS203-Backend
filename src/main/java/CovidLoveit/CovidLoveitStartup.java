package CovidLoveit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "CovidLoveit.Repositories.Interfaces")
public class CovidLoveitStartup {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CovidLoveitStartup.class, args);
    }

}