package ru.otus.java.advanced.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.java.advanced.client.entity.Client;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    @Query(nativeQuery = true,
            value = """
            SELECT *
            FROM CLIENT.client c
            ORDER BY c.created_at DESC
            LIMIT 1
       """)
    Optional<Client> getLatestClient();
}
