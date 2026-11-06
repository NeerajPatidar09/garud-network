
import { useCallback, useEffect, useState } from "react";

import { deviceService } from "../services/deviceService";

import type {Device,DeviceSearchCriteria,} from "../types/deviceTypes";

export function useDevices(criteria: DeviceSearchCriteria = {},page = 0,size = 10) {

  const [devices, setDevices] = useState<Device[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [totalElements, setTotalElements] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const fetchDevices = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await deviceService.searchDevices(criteria, page, size);
      setDevices(response.content);
      setTotalElements(response.totalElements);
      setTotalPages(response.totalPages);
    } catch {setError("Unable to load devices.");
    } finally {
      setLoading(false);

    }

  }, [criteria, page, size]);

  useEffect(() => {
    void fetchDevices();
  }, [fetchDevices]);

  return {
    devices,
    loading,
    error,
    totalElements,
    totalPages,
    refreshDevices: fetchDevices,
  };
}
