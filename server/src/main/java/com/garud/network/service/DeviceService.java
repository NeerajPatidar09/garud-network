package com.garud.network.service;

import java.util.List;
import com.garud.network.dto.DeviceCreateRequest;
import com.garud.network.dto.DevicePageResponse;
import com.garud.network.dto.DeviceResponse;
import com.garud.network.dto.DeviceSearchCriteria;
import com.garud.network.dto.DeviceUpdateRequest;

public interface DeviceService {
    DeviceResponse createDevice(DeviceCreateRequest request);
    DeviceResponse getDeviceById(Long id);
    List<DeviceResponse> getAllDevices();
    DeviceResponse updateDevice(Long id,DeviceUpdateRequest request);
    void deleteDevice(Long id);
    DevicePageResponse<DeviceResponse> searchDevices(DeviceSearchCriteria criteria,int page,int size,String sortBy,String direction);
}
