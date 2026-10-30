package com.garud.network.repository;

import com.garud.network.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    Optional<Incident> findByIncidentNumber(String incidentNumber);
}
