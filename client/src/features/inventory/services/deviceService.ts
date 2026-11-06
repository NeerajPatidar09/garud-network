import apiClient from "../../../services/apiClient";

import type {
  Device,
  DeviceCreateRequest,
  DeviceUpdateRequest,
  DeviceSearchCriteria,
  DevicePageResponse,
} from "../types/deviceTypes";

export const deviceService = {

  async getAllDevices(): Promise<Device[]> {
    const response = await apiClient.get<Device[]>("/devices");
    return response.data;
  },

  async getDeviceById(id: number): Promise<Device> {
    const response = await apiClient.get<Device>(`/devices/${id}`);
    return response.data;
  },

  async createDevice(
    request: DeviceCreateRequest
  ): Promise<Device> {

    const response = await apiClient.post<Device>(
      "/devices",
      request
    );

    return response.data;
  },

  async updateDevice(
    id: number,
    request: DeviceUpdateRequest
  ): Promise<Device> {

    const response = await apiClient.put<Device>(
      `/devices/${id}`,
      request
    );

    return response.data;
  },

  async deleteDevice(id: number): Promise<void> {
    await apiClient.delete(`/devices/${id}`);
  },

  async searchDevices(
    criteria: DeviceSearchCriteria,
    page = 0,
    size = 10,
    sortBy = "hostname",
    direction = "asc"
  ): Promise<DevicePageResponse<Device>> {

    const response =
      await apiClient.get<DevicePageResponse<Device>>(
        "/devices/search",
        {
          params: {
            ...criteria,
            page,
            size,
            sortBy,
            direction,
          },
        }
      );

    return response.data;
  },
};
