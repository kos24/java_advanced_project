package ru.otus.java.advanced.buildingobjectmonitoring.service.decl;

import java.time.LocalDate;

public interface PlanDateCalcService {

    LocalDate calculateMonitoringPlanDate(Integer riskScore);
    LocalDate updateMonitoringPlanDate(Integer riskScore, LocalDate planDate);
}
