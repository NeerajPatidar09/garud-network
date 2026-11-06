package com.garud.network.dto;

import com.garud.network.model.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DeviceCreateRequest(

    @NotBlank(message = "Hostname is required")
    @Size(max = 100, message = "Hostname cannot exceed 100 characters")
    String hostname,

    @NotBlank(message = "IP address is required")
    @Size(max = 45, message = "IP address cannot exceed 45 characters")
    @Pattern(
        regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$|^[0-9a-fA-F:]+$",
        message = "Invalid IP address format"
    )
    String ipAddress,

    @NotNull(message = "Device type is required")
    DeviceType deviceType,

    @Size(max = 100)
    String vendor,

    @Size(max = 100)
    String model,

    @Size(max = 100)
    String serialNumber,

    @Size(max = 150)
    String location,

    @Size(max = 2000)
    String description

) {}
