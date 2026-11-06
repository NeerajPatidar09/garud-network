package com.garud.network.dto;

import com.garud.network.model.DeviceStatus;
import com.garud.network.model.DeviceType;

public record DeviceSearchCriteria(
    String keyword,
    DeviceType deviceType,
    DeviceStatus status,
    String vendor,
    String location
) {}