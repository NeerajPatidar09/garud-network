export type DeviceType =
  | "ROUTER"
  | "SWITCH"
  | "FIREWALL"
  | "SERVER"
  | "ACCESS_POINT"
  | "LOAD_BALANCER"
  | "OTHER";

export type DeviceStatus =
  | "ACTIVE"
  | "INACTIVE"
  | "MAINTENANCE"
  | "DECOMMISSIONED";

export interface Device {
  id: number;
  hostname: string;
  ipAddress: string;
  deviceType: DeviceType;
  vendor: string | null;
  model: string | null;
  serialNumber: string | null;
  location: string | null;
  status: DeviceStatus;
  description: string | null;
  createdAt: string;
  updatedAt: string;
}

export interface DeviceCreateRequest {
  hostname: string;
  ipAddress: string;
  deviceType: DeviceType;
  vendor?: string;
  model?: string;
  serialNumber?: string;
  location?: string;
  description?: string;
}

export interface DeviceUpdateRequest
  extends DeviceCreateRequest {
  status: DeviceStatus;
}

export interface DeviceSearchCriteria {
  keyword?: string;
  deviceType?: DeviceType;
  status?: DeviceStatus;
  vendor?: string;
  location?: string;
}

export interface DevicePageResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
  hasNext: boolean;
  hasPrevious: boolean;
}
