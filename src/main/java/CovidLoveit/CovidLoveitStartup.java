package CovidLoveit;

import CovidLoveit.Domain.InputModel.ClientInputModel;
import CovidLoveit.Domain.InputModel.EmployeeRecordInputModel;
import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.InputModel.IndustryInputModel;
import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Service.Services.Interfaces.ClientService;
import CovidLoveit.Service.Services.Interfaces.EmployeeRecordService;
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
                          GuidelineService guidelineService, RegisteredBusinessService businessService,
                          EmployeeRecordService employeeRecordService) {
        return args -> {
            // TODO: Remove seed data from here
            clientService.addRole(new Role("ADMIN"));
            clientService.addRole(new Role("USER"));

            var adminUser = clientService.addClient(new ClientInputModel("linsyhen99@gmail.com", "123456"));
            var user = clientService.addClient(new ClientInputModel("ruwansadris.2020@smu.edu.sg", "123456"));

            clientService.addRoleToClient("linsyhen99@gmail.com", "ADMIN");
            clientService.addRoleToClient("ruwansadris.2020@smu.edu.sg", "USER");

            var industry = industryService.addIndustry(adminUser.getClientId().toString(), new IndustryInputModel("Food & Beverage", "Hawker", "SFA coffee shop / eating house / canteen license"));
            var anotherIndustry = industryService.addIndustry(adminUser.getClientId().toString(), new IndustryInputModel("Entertainment", "Fitness First", "Bro come workout and you wont regret."));

            var business = businessService.addBusiness("Yeow Leong Chicken Rice", "welcome! let's Chicken rice!", industry.getIndustryId(), user.getClientId());

            guidelineService.addGuideline(adminUser.getClientId().toString(), new GuidelineInputModel(
                    true,
                    "F&B establishments are allowed to continue food service operations, with the exception of establishments with Pubs, Bars, Nightclubs, Discos and Karaoke SFA license categories or SSIC codes starting with 5613.",
                    2,
                    "As hawker centres and coffeeshops are open-air and naturally ventilated spaces, a special concession would be given to allow vaccinated and unvaccinated individuals to dine in these settings, but subject to group sizes of up to 2 persons only.",
                    7,
                    14,
                    "From 15 July 2021, all individuals working at F&B establishments providing dine-in services must undergo testing once every 14 days using tests",
                    "SafeEntry",
                    "F&B establishments must implement SafeEntry via TraceTogether-only SafeEntry (TT-only SE) for customers and visitors, with the exception of those that only provide takeaway and/or delivery, with no dine-in services",
                    75,
                    "Only allowed 75% capacity at each time slot",
                    "Clearly demarcate queue lines, put up signage to guide customers on where to queue to order and collect food, and ensure at least one-metre spacing between individual customers at areas such as entrances and cashier counters.",
                    "https://www.stb.gov.sg/content/stb/en/home-pages/advisory-for-food-and-beverage-establishments.html#_ftn2",
                    industry.getIndustryId()
            ));

            EmployeeRecordInputModel employee = new EmployeeRecordInputModel("John Smith", "08/08/1998", "PFIZER", "10/10/2021", "NEGATIVE", business.getBusinessId());
            employeeRecordService.addEmployee(employee);
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