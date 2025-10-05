package ru.otus.java.advanced.buildingobjectmonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableJpaAuditing
@EnableCaching
@EnableRetry
public class BuildingObjectMonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuildingObjectMonitoringApplication.class, args);
    }

}
