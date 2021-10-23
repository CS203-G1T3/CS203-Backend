package CovidLoveit;

import CovidLoveit.Domain.Models.Email;
import CovidLoveit.Service.Services.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "CovidLoveit.Repositories.Interfaces")
public class CovidLoveitStartup {

    @Autowired
    private EmailServiceImpl emailService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CovidLoveitStartup.class, args);
    }
}