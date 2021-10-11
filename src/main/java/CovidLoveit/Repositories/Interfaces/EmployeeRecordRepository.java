package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.EmployeeRecord;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeRecordRepository extends JpaRepository<EmployeeRecord, UUID> {

    List<EmployeeRecord> findEmployeeRecordByBusiness(RegisteredBusiness business);
}
