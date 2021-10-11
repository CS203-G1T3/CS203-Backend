package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.EmployeeRecord;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DataJpaTest
class EmployeeRecordRepositoryTest {

    @Autowired
    private RegisteredBusinessRepository businessTestRepository;
    @Autowired
    private EmployeeRecordRepository underTest;

    @AfterEach
    void tearDown() {
        businessTestRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void checkIfEmployeeExistsByBusiness() {
        // given
        RegisteredBusiness business = new RegisteredBusiness("Food & Beverage", "We sell food and drinks");
        RegisteredBusiness businessRecord = businessTestRepository.save(business);
        EmployeeRecord employee = new EmployeeRecord(
                "Rubin",
                LocalDate.now(),
                "Pfizer",
                LocalDate.now(),
                "Positive"
        );

        underTest.save(employee);
        employee.setBusiness(businessRecord);

        // when
        List<EmployeeRecord> expected = underTest.findEmployeeRecordByBusiness(business);

        // then
        EmployeeRecord expectedEmployee = expected.get(0);
        assertThat(
              expectedEmployee.getEmployeeName().equals(employee.getEmployeeName()) &&
              expectedEmployee.getDateOfBirth().equals(employee.getDateOfBirth()) &&
              expectedEmployee.getVaccine().equals(employee.getVaccine()) &&
              expectedEmployee.getLastSwabDate().equals(employee.getLastSwabDate()) &&
              expectedEmployee.getSwabResult().equals(employee.getSwabResult()) &&
              expectedEmployee.getBusiness().getBusinessId().equals(employee.getBusiness().getBusinessId()) &&
              expectedEmployee.getBusiness().getBusinessName().equals(employee.getBusiness().getBusinessName()) &&
              expectedEmployee.getBusiness().getBusinessDesc().equals(employee.getBusiness().getBusinessDesc())
        ).isTrue();
    }

    @Test
    void checkIfEmployeeDoesNotExistByBusiness() {
        // given
        RegisteredBusiness business = new RegisteredBusiness("Food & Beverage", "We sell food and drinks");
        RegisteredBusiness businessRecord = businessTestRepository.save(business);

        // when
        List<EmployeeRecord> expected = underTest.findEmployeeRecordByBusiness(business);

        // then
        assertThat(expected.isEmpty()).isTrue();
    }
}