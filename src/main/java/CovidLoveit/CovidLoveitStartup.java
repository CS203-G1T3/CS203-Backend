package CovidLoveit;

import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Service.Services.Interfaces.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@Configuration
@EnableJpaRepositories(basePackages = "CovidLoveit.Repositories.Interfaces")
public class CovidLoveitStartup {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CovidLoveitStartup.class, args);
    }

    @Bean
    CommandLineRunner run(ClientService clientService) {
        return args -> {
            // TODO: Remove seed data from here
            clientService.addRole(new Role("ADMIN"));
            clientService.addRole(new Role("USER"));

            clientService.addClient(new Client("123456", "linsyhen99@gmail.com"));
            clientService.addClient(new Client("123456", "ruwansadris.2020@smu.edu.sg"));

            clientService.addRoleToClient("linsyhen99@gmail.com", "ADMIN");
            clientService.addRoleToClient("ruwansadris.2020@smu.edu.sg", "USER");
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}