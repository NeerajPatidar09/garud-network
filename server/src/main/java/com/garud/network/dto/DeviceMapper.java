package com.garud.network.dto;

import org.springframework.stereotype.Component;
import com.garud.network.model.Device;

@Component
public class DeviceMapper {

    public Device toEntity(DeviceCreateRequest request) {

        Device device = new Device(
            request.hostname(),
            request.ipAddress(),
            request.deviceType()
        );

        device.setVendor(request.vendor());
        device.setModel(request.model());
        device.setSerialNumber(request.serialNumber());
        device.setLocation(request.location());
        device.setDescription(request.description());

        return device;
    }

    public void updateEntity(
        Device device,
        DeviceUpdateRequest request
    ) {

        device.setHostname(request.hostname());
        device.setIpAddress(request.ipAddress());
        device.setDeviceType(request.deviceType());
        device.setVendor(request.vendor());
        device.setModel(request.model());
        device.setSerialNumber(request.serialNumber());
        device.setLocation(request.location());
        device.setStatus(request.status());
        device.setDescription(request.description());
    }

    public DeviceResponse toResponse(Device device) {

        return new DeviceResponse(
            device.getId(),
            device.getHostname(),
            device.getIpAddress(),
            device.getDeviceType(),
            device.getVendor(),
            device.getModel(),
            device.getSerialNumber(),
            device.getLocation(),
            device.getStatus(),
            device.getDescription(),
            device.getCreatedAt(),
            device.getUpdatedAt()
        );
    }
}
