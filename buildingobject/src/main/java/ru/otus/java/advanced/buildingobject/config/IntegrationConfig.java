package ru.otus.java.advanced.buildingobject.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.handler.LoggingHandler;
import ru.otus.java.advanced.buildingobject.service.impl.BuildingObjectDispatcher;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class IntegrationConfig {

    private final BuildingObjectDispatcher buildingObjectDispatcher;

    @Bean
    public DirectChannel outboundMonitoringChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow objectMonitoringFlow() {
        return IntegrationFlow.from(outboundMonitoringChannel())
                .log(LoggingHandler.Level.INFO)
                .handle(buildingObjectDispatcher)
                .get();
    }
}
