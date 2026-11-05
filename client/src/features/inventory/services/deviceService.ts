import apiClient from "../../../services/apiClient";

export interface Device {
  id: number;
  hostname: string;
  ipAddress: string;
  deviceType: string;
  status: string;
}

export const deviceService = {

  async getAllDevices(): Promise<Device[]> {

    const response =
      await apiClient.get<Device[]>("/devices");

    return response.data;
  },

  async getDeviceById(
    id: number
  ): Promise<Device> {

    const response =
      await apiClient.get<Device>(`/devices/${id}`);

    return response.data;
  },

};