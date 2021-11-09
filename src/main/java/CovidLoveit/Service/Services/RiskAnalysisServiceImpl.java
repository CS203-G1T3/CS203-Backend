package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.EmployeeRecord;
import CovidLoveit.Exception.RegisteredBusinessException;
import CovidLoveit.Repositories.Interfaces.EmployeeRecordRepository;
import CovidLoveit.Repositories.Interfaces.RegisteredBusinessRepository;
import CovidLoveit.Service.Services.Interfaces.RiskAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
public class RiskAnalysisServiceImpl implements RiskAnalysisService {

    private Logger logger = LoggerFactory.getLogger(RiskAnalysisServiceImpl.class);
    private final EmployeeRecordRepository employeeRecordRepository;
    private final RegisteredBusinessRepository registeredBusinessRepository;

    @Autowired
    public RiskAnalysisServiceImpl(EmployeeRecordRepository employeeRecordRepository,
                                   RegisteredBusinessRepository registeredBusinessRepository) {
        this.employeeRecordRepository = employeeRecordRepository;
        this.registeredBusinessRepository = registeredBusinessRepository;
    }

    @Override
    public int getVaccinationRateRiskByBusinessId(UUID businessId) {
        var business = registeredBusinessRepository.findById(businessId);
        business.orElseThrow(() -> {
            logger.warn(String.format("Business with ID {%s} not found in DB.", businessId));
            throw new RegisteredBusinessException(String.format("Business with ID {%s} not found in DB.", businessId));
        });

        var employeeRecords = employeeRecordRepository.findEmployeeRecordByBusiness(business.get());

        int vaccinationRate = 0;
        for(EmployeeRecord record : employeeRecords) {
            if (record.getVaccine() != null) {
                vaccinationRate++;
            }
        }

        return 6 - (((vaccinationRate / employeeRecords.size()) * 100) / 20);
    }

    @Override
    public int getCovidTestingRiskByBusinessId(UUID businessId) {
        var business = registeredBusinessRepository.findById(businessId);
        business.orElseThrow(() -> {
            logger.warn(String.format("Business with ID {%s} not found in DB.", businessId));
            throw new RegisteredBusinessException(String.format("Business with ID {%s} not found in DB.", businessId));
        });

        var employeeRecords = employeeRecordRepository.findEmployeeRecordByBusiness(business.get());

        int lastSwabTest = 0;
        var localDate = LocalDate.now();
        for(EmployeeRecord record : employeeRecords) {
            long dateDifference = DAYS.between(record.getLastSwabDate(), localDate);
            lastSwabTest += (int)dateDifference;
        }

        return Math.min(5, lastSwabTest / employeeRecords.size() / 2);
    }

    @Override
    public int getAverageAgeRiskByBusinessId(UUID businessId) {
        var business = registeredBusinessRepository.findById(businessId);
        business.orElseThrow(() -> {
            logger.warn(String.format("Business with ID {%s} not found in DB.", businessId));
            throw new RegisteredBusinessException(String.format("Business with ID {%s} not found in DB.", businessId));
        });

        var employeeRecords = employeeRecordRepository.findEmployeeRecordByBusiness(business.get());

        int totalAge = 0;
        var localDate = LocalDate.now();
        for(EmployeeRecord record : employeeRecords) {
            var birthDate = record.getDateOfBirth();

            if (localDate != null && birthDate != null) {
                totalAge += Period.between(birthDate, localDate).getYears();
            }
        }

        return Math.min(4, totalAge / employeeRecords.size() / 20);
    }
}
