package com.garud.network.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.garud.network.dto.DeviceCreateRequest;
import com.garud.network.dto.DevicePageResponse;
import com.garud.network.dto.DeviceResponse;
import com.garud.network.dto.DeviceSearchCriteria;
import com.garud.network.dto.DeviceUpdateRequest;
import com.garud.network.model.DeviceStatus;
import com.garud.network.model.DeviceType;
import com.garud.network.service.DeviceService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/devices")
@Validated
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    // CREATE DEVICE
    @PostMapping
    public ResponseEntity<DeviceResponse> createDevice(@Valid @RequestBody DeviceCreateRequest request) {

        DeviceResponse response = deviceService.createDevice(request);
        URI location = URI.create("/api/v1/devices/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    // GET DEVICE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDeviceById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    // GET ALL DEVICES
    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    // UPDATE DEVICE
    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponse> updateDevice(@PathVariable Long id,
            @Valid @RequestBody DeviceUpdateRequest request) {
        return ResponseEntity.ok(deviceService.updateDevice(id, request));
    }

    // DELETE DEVICE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<DevicePageResponse<DeviceResponse>> searchDevices(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) DeviceType deviceType,
            @RequestParam(required = false) DeviceStatus status,
            @RequestParam(required = false) String vendor,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "hostname") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        DeviceSearchCriteria criteria = new DeviceSearchCriteria(
                keyword,
                deviceType,
                status,
                vendor,
                location);

        return ResponseEntity.ok(
                deviceService.searchDevices(
                        criteria,
                        page,
                        size,
                        sortBy,
                        direction));
    }
}
