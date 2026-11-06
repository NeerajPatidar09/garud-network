package com.garud.network.dto;

import com.garud.network.model.DeviceStatus;
import com.garud.network.model.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DeviceUpdateRequest(

    @NotBlank
    @Size(max = 100)
    String hostname,

    @NotBlank
    @Size(max = 45)
    @Pattern(
        regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$|^[0-9a-fA-F:]+$",
        message = "Invalid IP address format"
    )
    String ipAddress,

    @NotNull
    DeviceType deviceType,

    @Size(max = 100)
    String vendor,

    @Size(max = 100)
    String model,

    @Size(max = 100)
    String serialNumber,

    @Size(max = 150)
    String location,

    @NotNull
    DeviceStatus status,

    @Size(max = 2000)
    String description

) {}

