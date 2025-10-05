package ru.otus.java.advanced.dictionaryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.java.advanced.dictionaryservice.entity.Dictionary;

import java.util.Optional;
import java.util.UUID;

public interface DictionaryRepository extends JpaRepository<Dictionary, UUID> {

    Optional<Dictionary> findByCode(String name);
}
