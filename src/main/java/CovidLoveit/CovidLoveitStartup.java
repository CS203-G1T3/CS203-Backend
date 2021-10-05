package CovidLoveit;

import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.InputModel.IndustryInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Service.Services.Interfaces.ClientService;
import CovidLoveit.Service.Services.Interfaces.GuidelineService;
import CovidLoveit.Service.Services.Interfaces.IndustryService;
import CovidLoveit.Service.Services.Interfaces.RegisteredBusinessService;
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
    CommandLineRunner run(ClientService clientService, IndustryService industryService,
                          GuidelineService guidelineService, RegisteredBusinessService businessService) {
        return args -> {
            // TODO: Remove seed data from here
            clientService.addRole(new Role("ADMIN"));
            clientService.addRole(new Role("USER"));

            var adminUser = clientService.addClient(new Client("123456", "linsyhen99@gmail.com"));
            var user = clientService.addClient(new Client("123456", "ruwansadris.2020@smu.edu.sg"));

            clientService.addRoleToClient("linsyhen99@gmail.com", "ADMIN");
            clientService.addRoleToClient("ruwansadris.2020@smu.edu.sg", "USER");


            var industry = industryService.addIndustry(adminUser.getClientId().toString(), new IndustryInputModel("Food & Beverage", "Hawker", "Hawker actually sells good food"));

            businessService.addBusiness("ABC food kind", "TSL comes here quite often i think", industry.getIndustryId(), user.getClientId());

            guidelineService.addGuideline(adminUser.getClientId().toString(), new GuidelineInputModel(
                    true,
                    "Sure why not",
                    2,
                    "Mingle in groups of 2",
                    5000,
                    5000000,
                    "Tested negative",
                    "+6593959697",
                    "This is my number",
                    50,
                    "Only allowed 50 workers at each time slot",
                    "Operate as per normal",
                    "www.facebook.com",
                    industry.getIndustryId()
            ));
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