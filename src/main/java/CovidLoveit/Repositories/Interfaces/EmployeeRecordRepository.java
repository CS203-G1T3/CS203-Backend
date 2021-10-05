package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.EmployeeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRecordRepository extends JpaRepository<EmployeeRecord, String> {

    @Query(value = "SELECT * FROM EmployeeRecord WHERE businessId = :#{#business_id}", nativeQuery = true)
    List<EmployeeRecord> getEmployeeByBusiness(@Param("business_id") String businessId);
}
