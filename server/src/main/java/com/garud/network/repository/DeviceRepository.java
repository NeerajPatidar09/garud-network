package com.garud.network.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.garud.network.model.Device;
import com.garud.network.model.DeviceStatus;
import com.garud.network.model.DeviceType;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByHostname(String hostname);
    Optional<Device> findByIpAddress(String ipAddress);
    List<Device> findByDeviceType(DeviceType deviceType);
    List<Device> findByVendorIgnoreCase(String vendor);
    boolean existsByHostname(String hostname);
    boolean existsBySerialNumber(String serialNumber);
    Optional<Device> findBySerialNumber(String serialNumber);
    List<Device> findByStatus(DeviceStatus status);
    boolean existsByIpAddress(String ipAddress);
    boolean existsByHostnameAndIdNot(String hostname, Long id);
    boolean existsByIpAddressAndIdNot(String ipAddress, Long id);
    boolean existsBySerialNumberAndIdNot(String serialNumber,Long id);
    Page<Device> findByHostnameContainingIgnoreCase(String hostname,Pageable pageable);
}