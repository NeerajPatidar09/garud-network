
import { useEffect, useState } from "react";

import {
  Link,
  useParams,
} from "react-router";

import { deviceService } from "../../../services/deviceService";

import type { Device } from "../types/deviceTypes";

export default function DeviceDetailsPage() {

  const { id } = useParams<{ id: string }>();

  const [device, setDevice] = useState<Device | null>(null);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState<string | null>(null);

  useEffect(() => {

    async function fetchDevice() {

      try {

        if (!id || !/^\d+$/.test(id)) {
          throw new Error("Invalid device ID");
        }

        const response = await deviceService.getDeviceById(
          Number(id)
        );

        setDevice(response);

      } catch {

        setError("Unable to load device details.");

      } finally {

        setLoading(false);

      }
    }

    void fetchDevice();

  }, [id]);

  if (loading) {
    return <div className="page-container">Loading device...</div>;
  }

  if (error || !device) {
    return (
      <div className="page-container">
        <p role="alert">{error ?? "Device not found."}</p>
        <Link to="/devices">Back to Inventory</Link>
      </div>
    );
  }

  return (
    <div className="page-container">

      <div className="page-header">

        <div>
          <Link to="/devices">← Back to Inventory</Link>

          <h1>{device.hostname}</h1>

          <p>Device ID: {device.id}</p>
        </div>

        <span
          className={`status-badge ${device.status.toLowerCase()}`}
        >
          {device.status.replaceAll("_", " ")}
        </span>

      </div>

      <div className="device-details-grid">

        <div className="content-card device-info-card">

          <h2>General Information</h2>

          <div className="detail-row">
            <span>Hostname</span>
            <strong>{device.hostname}</strong>
          </div>

          <div className="detail-row">
            <span>IP Address</span>
            <strong>{device.ipAddress}</strong>
          </div>

          <div className="detail-row">
            <span>Device Type</span>
            <strong>{device.deviceType.replaceAll("_", " ")}</strong>
          </div>

          <div className="detail-row">
            <span>Status</span>
            <strong>{device.status}</strong>
          </div>

        </div>

        <div className="content-card device-info-card">

          <h2>Hardware Information</h2>

          <div className="detail-row">
            <span>Vendor</span>
            <strong>{device.vendor ?? "-"}</strong>
          </div>

          <div className="detail-row">
            <span>Model</span>
            <strong>{device.model ?? "-"}</strong>
          </div>

          <div className="detail-row">
            <span>Serial Number</span>
            <strong>{device.serialNumber ?? "-"}</strong>
          </div>

        </div>

        <div className="content-card device-info-card">

          <h2>Location Information</h2>

          <div className="detail-row">
            <span>Location</span>
            <strong>{device.location ?? "-"}</strong>
          </div>

        </div>

        <div className="content-card device-info-card">

          <h2>Audit Information</h2>

          <div className="detail-row">
            <span>Created At</span>
            <strong>
              {new Date(device.createdAt).toLocaleString()}
            </strong>
          </div>

          <div className="detail-row">
            <span>Last Updated</span>
            <strong>
              {new Date(device.updatedAt).toLocaleString()}
            </strong>
          </div>

        </div>

        <div className="content-card device-info-card full-width">

          <h2>Description</h2>

          <p>{device.description || "No description available."}</p>

        </div>

      </div>

    </div>
  );
}
