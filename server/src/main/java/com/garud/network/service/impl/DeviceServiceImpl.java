package com.garud.network.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.garud.network.dto.DeviceCreateRequest;
import com.garud.network.dto.DeviceMapper;
import com.garud.network.dto.DevicePageResponse;
import com.garud.network.dto.DeviceResponse;
import com.garud.network.dto.DeviceSearchCriteria;
import com.garud.network.dto.DeviceUpdateRequest;
import com.garud.network.expection.DuplicateResourceException;
import com.garud.network.expection.ResourceNotFoundException;
import com.garud.network.model.Device;
import com.garud.network.repository.DeviceRepository;
import com.garud.network.repository.DeviceSpecification;
import com.garud.network.service.DeviceService;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    public DeviceServiceImpl(DeviceRepository deviceRepository,DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    @Override
    public DeviceResponse createDevice(DeviceCreateRequest request) {

        validateUniqueFields(
            request.hostname(),
            request.ipAddress(),
            request.serialNumber(),
            null
        );

        Device device = deviceMapper.toEntity(request);
        Device savedDevice = deviceRepository.save(device);
        return deviceMapper.toResponse(savedDevice);
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceResponse getDeviceById(Long id) {

        Device device = findDeviceById(id);
        return deviceMapper.toResponse(device);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceResponse> getAllDevices() {

        return deviceRepository.findAll()
            .stream()
            .map(deviceMapper::toResponse)
            .toList();
    }

    @Override
    public DeviceResponse updateDevice(Long id,DeviceUpdateRequest request) {

        Device device = findDeviceById(id);

        validateUniqueFields(
            request.hostname(),
            request.ipAddress(),
            request.serialNumber(),
            id
        );

        deviceMapper.updateEntity(device, request);
        Device updatedDevice = deviceRepository.save(device);
        return deviceMapper.toResponse(updatedDevice);
    }

    @Override
    public void deleteDevice(Long id) {

        Device device = findDeviceById(id);
        deviceRepository.delete(device);
    }

    private Device findDeviceById(Long id) {

        return deviceRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Device not found with ID: " + id
                )
            );
    }

    private void validateUniqueFields(
        String hostname,
        String ipAddress,
        String serialNumber,
        Long currentDeviceId
    ) {

        boolean hostnameExists = currentDeviceId == null
            ? deviceRepository.existsByHostname(hostname)
            : deviceRepository.existsByHostnameAndIdNot(
                hostname, currentDeviceId
            );

        if (hostnameExists) {
            throw new DuplicateResourceException(
                "Device hostname already exists: " + hostname
            );
        }

        boolean ipExists = currentDeviceId == null
            ? deviceRepository.existsByIpAddress(ipAddress)
            : deviceRepository.existsByIpAddressAndIdNot(
                ipAddress, currentDeviceId
            );

        if (ipExists) {
            throw new DuplicateResourceException(
                "Device IP address already exists: " + ipAddress
            );
        }

        if (serialNumber != null && !serialNumber.isBlank()) {

            boolean serialExists = currentDeviceId == null
                ? deviceRepository.existsBySerialNumber(serialNumber)
                : deviceRepository.existsBySerialNumberAndIdNot(
                    serialNumber, currentDeviceId
                );

            if (serialExists) {
                throw new DuplicateResourceException(
                    "Device serial number already exists: " + serialNumber
                );
            }
        }
    }

    @Override
@Transactional(readOnly = true)
public DevicePageResponse<DeviceResponse> searchDevices(
    DeviceSearchCriteria criteria,
    int page,
    int size,
    String sortBy,
    String direction
) {

    if (page < 0) {
        throw new IllegalArgumentException(
            "Page number cannot be negative"
        );
    }

    if (size < 1 || size > 100) {
        throw new IllegalArgumentException(
            "Page size must be between 1 and 100"
        );
    }

    List<String> allowedSortFields = List.of(
        "id",
        "hostname",
        "ipAddress",
        "deviceType",
        "status",
        "vendor",
        "createdAt"
    );

    if (!allowedSortFields.contains(sortBy)) {
        throw new IllegalArgumentException(
            "Invalid sort field: " + sortBy
        );
    }

    Sort.Direction sortDirection =
        "desc".equalsIgnoreCase(direction)
            ? Sort.Direction.DESC
            : Sort.Direction.ASC;

    Pageable pageable = PageRequest.of(
        page,
        size,
        Sort.by(sortDirection, sortBy)
    );

    Page<Device> devicePage = deviceRepository.findAll(
        DeviceSpecification.filterDevices(
            criteria.keyword(),
            criteria.deviceType(),
            criteria.status(),
            criteria.vendor(),
            criteria.location()
        ),
        pageable
    );

    List<DeviceResponse> content = devicePage
        .getContent()
        .stream()
        .map(deviceMapper::toResponse)
        .toList();

    return new DevicePageResponse<>(
        content,
        devicePage.getNumber(),
        devicePage.getSize(),
        devicePage.getTotalElements(),
        devicePage.getTotalPages(),
        devicePage.isFirst(),
        devicePage.isLast(),
        devicePage.hasNext(),
        devicePage.hasPrevious()
    );
}
}
