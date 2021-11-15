package CovidLoveit.Utils;

import CovidLoveit.Domain.InputModel.*;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Service.Services.Interfaces.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SeedData {

//    @Bean
//    CommandLineRunner run(ClientService clientService, IndustryService industryService,
//                          GuidelineService guidelineService, RegisteredBusinessService businessService,
//                          EmployeeRecordService employeeRecordService) {
//        return args -> {
//            // TODO: Remove seed data from here
//            clientService.addRole(new Role("ADMIN"));
//            clientService.addRole(new Role("USER"));
//
//            var adminUser = clientService.addClient(new ClientInputModel("linsyhen99@gmail.com", "123456"));
//            var user = clientService.addClient(new ClientInputModel("ruwansadris.2020@smu.edu.sg", "123456"));
//
//            clientService.addRoleToClient("linsyhen99@gmail.com", "ADMIN");
//            clientService.addRoleToClient("ruwansadris.2020@smu.edu.sg", "USER");
//
//            var industry = industryService.addIndustry(adminUser.getClientId().toString(), new IndustryInputModel("Food & Beverage", "Hawker", "SFA coffee shop / eating house / canteen license"));
//            var anotherIndustry = industryService.addIndustry(adminUser.getClientId().toString(), new IndustryInputModel("Entertainment", "Fitness First", "Bro come workout and you wont regret."));
//
//            var business = businessService.addBusiness(new RegisteredBusinessInputModel("Yeow Leong Chicken Rice", "welcome! let's Chicken rice!", industry.getIndustryId(), user.getClientId()));
//
//            guidelineService.addGuideline(adminUser.getClientId().toString(), new GuidelineInputModel(
//                    true,
//                    "F&B establishments are allowed to continue food service operations, with the exception of establishments with Pubs, Bars, Nightclubs, Discos and Karaoke SFA license categories or SSIC codes starting with 5613.",
//                    2,
//                    "As hawker centres and coffeeshops are open-air and naturally ventilated spaces, a special concession would be given to allow vaccinated and unvaccinated individuals to dine in these settings, but subject to group sizes of up to 2 persons only.",
//                    7,
//                    14,
//                    "From 15 July 2021, all individuals working at F&B establishments providing dine-in services must undergo testing once every 14 days using tests",
//                    "SafeEntry",
//                    "F&B establishments must implement SafeEntry via TraceTogether-only SafeEntry (TT-only SE) for customers and visitors, with the exception of those that only provide takeaway and/or delivery, with no dine-in services",
//                    75,
//                    "Only allowed 75% capacity at each time slot",
//                    "Clearly demarcate queue lines, put up signage to guide customers on where to queue to order and collect food, and ensure at least one-metre spacing between individual customers at areas such as entrances and cashier counters.",
//                    "https://www.stb.gov.sg/content/stb/en/home-pages/advisory-for-food-and-beverage-establishments.html#_ftn2",
//                    industry.getIndustryId().toString()
//            ));
//
//            EmployeeRecordInputModel employee = new EmployeeRecordInputModel("John Smith", "08/08/1998", "PFIZER", "10/10/2021", "NEGATIVE", business.getBusinessId());
//            employeeRecordService.addEmployee(employee);
//        };
//    }
//    @Bean
//    CommandLineRunner run(ClientService clientService, IndustryService industryService,
//                          GuidelineService guidelineService, RegisteredBusinessService businessService,
//                          EmployeeRecordService employeeRecordService) {
//        return args -> {
//            // TODO: Remove seed data from here
//            clientService.addRole(new Role("ADMIN"));
//            clientService.addRole(new Role("USER"));
//
//            var adminUser = clientService.addClient(new ClientInputModel("rahmat329@gmail.com", "123456"));
////            var adminUserTest = clientService.addClient(new ClientInputModel("webtestercs102@gmail.com", "123456"));
//            var user = clientService.addClient(new ClientInputModel("mohamadry.2020@smu.edu.sg", "123456"));
//
//            clientService.addRoleToClient("rahmat329@gmail.com", "ADMIN");
////            clientService.addRoleToClient("webtestercs102@gmail.com", "ADMIN");
//            clientService.addRoleToClient("mohamadry.2020@smu.edu.sg", "USER");
//
//            var industry = industryService.addIndustry(adminUser.getClientId().toString(), new IndustryInputModel("Food & Beverage", "Hawker", "SFA coffee shop / eating house / canteen license"));
//            var anotherIndustry = industryService.addIndustry(adminUser.getClientId().toString(), new IndustryInputModel("Entertainment", "Fitness First", "Bro come workout and you wont regret."));
//
//            var business = businessService.addBusiness(new RegisteredBusinessInputModel("Yeow Leong Chicken Rice", "welcome! let's Chicken rice!", industry.getIndustryId(), adminUser.getClientId()));
////            var businessTest = businessService.addBusiness(new RegisteredBusinessInputModel("Yeow Leong Chicken Rice", "welcome! let's Chicken rice!", industry.getIndustryId(), adminUserTest.getClientId()));
//
//            guidelineService.addGuideline(adminUser.getClientId().toString(), new GuidelineInputModel(
//                    true,
//                    "F&B establishments are allowed to continue food service operations, with the exception of establishments with Pubs, Bars, Nightclubs, Discos and Karaoke SFA license categories or SSIC codes starting with 5613.",
//                    2,
//                    "As hawker centres and coffeeshops are open-air and naturally ventilated spaces, a special concession would be given to allow vaccinated and unvaccinated individuals to dine in these settings, but subject to group sizes of up to 2 persons only.",
//                    7,
//                    14,
//                    "From 15 July 2021, all individuals working at F&B establishments providing dine-in services must undergo testing once every 14 days using tests",
//                    "SafeEntry",
//                    "F&B establishments must implement SafeEntry via TraceTogether-only SafeEntry (TT-only SE) for customers and visitors, with the exception of those that only provide takeaway and/or delivery, with no dine-in services",
//                    75,
//                    "Only allowed 75% capacity at each time slot",
//                    "Clearly demarcate queue lines, put up signage to guide customers on where to queue to order and collect food, and ensure at least one-metre spacing between individual customers at areas such as entrances and cashier counters.",
//                    "https://www.stb.gov.sg/content/stb/en/home-pages/advisory-for-food-and-beverage-establishments.html#_ftn2",
//                    industry.getIndustryId().toString()
//            ));
//
//            EmployeeRecordInputModel employee = new EmployeeRecordInputModel("John Smith", "08/08/1998", "PFIZER", "10/10/2021", "NEGATIVE", business.getBusinessId());
//            employeeRecordService.addEmployee(employee);
//        };
//    }
}
