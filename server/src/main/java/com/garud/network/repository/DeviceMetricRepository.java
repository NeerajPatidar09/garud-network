package com.garud.network.repository;

import com.garud.network.model.DeviceMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public interface DeviceMetricRepository extends JpaRepository<DeviceMetric, Long> {
    List<DeviceMetric> findByDeviceIdOrderByRecordedAtDesc(Long deviceId, Instant startTime);
    List<DeviceMetric> findByDeviceIdAndRecordedAtGreaterThanEqual(Long deviceId, Instant time);
}
