package ru.otus.java.advanced.client.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.client.entity.Client;
import ru.otus.java.advanced.client.repository.ClientRepository;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientRiskScoreReCalculationScheduler {

    private final ClientRepository clientRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topic.alert.risk.score:risk.score.notification}")
    private String topic;

    @Scheduled(cron = "${recalculate.risk.score.scheduler:0 0/1 * * * *}")
    public void process() {
        Optional<Client> latestClient = clientRepository.getLatestClient();
        latestClient.ifPresent(client -> {
            Random random = new Random();
            client.setClientReliability(random.nextInt(6));
            client.setCreditHistory(random.nextInt(6));
            client.setEscalationCount(random.nextInt(6));
            clientRepository.save(client);
            try {
                kafkaTemplate.send(topic, client.getId().toString())
                        .whenComplete(
                                (result, ex) -> {
                                    if (ex == null) {
                                        log.info(
                                                "Successfully sent updated risk score notification for client ID: {}",
                                                client.getId());
                                    } else {
                                        log.error("Failed to send updated risk score notification for client ID: {}", client.getId(), ex);
                                    }
                                });
            } catch (Exception e) {
                log.error("Failed to send updated risk score notification for client ID: {}", client.getId(), e);
            }
        });
    }
}
