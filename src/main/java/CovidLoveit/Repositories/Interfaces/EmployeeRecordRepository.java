package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.EmployeeRecord;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRecordRepository extends JpaRepository<EmployeeRecord, String> {

    List<EmployeeRecord> findEmployeeRecordByBusiness(RegisteredBusiness business);
}
