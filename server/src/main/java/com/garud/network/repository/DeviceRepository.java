package com.garud.network.repository;

import com.garud.network.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByIpAddress(String ipAddress);
    List<Device> findByIsSimulatedTrueAndIsEnabledTrue();
    List<Device> findByStatusAndIsEnabledTrue(String status);
}
