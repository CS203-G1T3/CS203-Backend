package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.EmployeeRecord;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRecordRepository extends JpaRepository<EmployeeRecord, String> {

    List<EmployeeRecord> findEmployeeRecordByBusiness(RegisteredBusiness business);
}
