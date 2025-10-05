package ru.otus.java.advanced.buildingobjectmonitoring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.buildingobjectmonitoring.service.decl.PlanDateCalcService;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanDateCalcServiceImpl implements PlanDateCalcService {
    private static final int MONITORING_DEFAULT_INTERVAL = 30;

    @Override
    public LocalDate calculateMonitoringPlanDate(Integer riskScore) {
        return calculate(riskScore, null);
    }

    @Override
    public LocalDate updateMonitoringPlanDate(Integer riskScore, LocalDate planDate) {
        return calculate(riskScore, planDate);
    }

    private LocalDate calculate(Integer riskScore, LocalDate existingPlanDate) {
        LocalDate now = LocalDate.now();
        LocalDate planDate = existingPlanDate == null ? now : existingPlanDate;
        LocalDate newPlanDate = planDate.plusDays(MONITORING_DEFAULT_INTERVAL / riskScore);
        return newPlanDate.isBefore(now) ? now : newPlanDate;
    }
}
