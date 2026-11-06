package com.garud.network.dto;

import java.time.Instant;
import com.garud.network.model.DeviceStatus;
import com.garud.network.model.DeviceType;

public record DeviceResponse(

    Long id,
    String hostname,
    String ipAddress,
    DeviceType deviceType,
    String vendor,
    String model,
    String serialNumber,
    String location,
    DeviceStatus status,
    String description,
    Instant createdAt,
    Instant updatedAt

) {}
