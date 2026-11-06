import { Link } from "react-router";
import { useState } from "react";
import { useDevices } from "../hooks/useDevices";

import type {
  DeviceSearchCriteria,
  DeviceType,
  DeviceStatus,
} from "../types/deviceTypes";

const deviceTypes: DeviceType[] = [
  "ROUTER",
  "SWITCH",
  "FIREWALL",
  "SERVER",
  "ACCESS_POINT",
  "LOAD_BALANCER",
  "OTHER",
];

const deviceStatuses: DeviceStatus[] = [
  "ACTIVE",
  "INACTIVE",
  "MAINTENANCE",
  "DECOMMISSIONED",
];

export default function DevicesPage() {

  const [keyword, setKeyword] = useState("");
  const [deviceType, setDeviceType] = useState("");
  const [status, setStatus] = useState("");
  const [vendor, setVendor] = useState("");
  const [page, setPage] = useState(0);

  const criteria: DeviceSearchCriteria = {
    keyword: keyword || undefined,
    deviceType: (deviceType || undefined) as DeviceType | undefined,
    status: (status || undefined) as DeviceStatus | undefined,
    vendor: vendor || undefined,
  };

  const {
    devices,
    loading,
    error,
    totalElements,
    totalPages,
  } = useDevices(criteria, page, 10);

  return (
    <div className="page-container">

      <div className="page-header">
        <div>
          <h1>Device Inventory</h1>
          <p>Manage and monitor network infrastructure devices.</p>
        </div>

        <button className="primary-button">
          + Add Device
        </button>
      </div>

      <div className="stats-grid">
        <div className="stat-card">
          <span>Total Devices</span>
          <h2>{totalElements}</h2>
        </div>

        <div className="stat-card">
          <span>Current Page</span>
          <h2>{page + 1}</h2>
        </div>
      </div>

      <div className="content-card">

        <div className="table-toolbar">

          <h2>All Devices</h2>

          <input
            type="search"
            placeholder="Search hostname, IP, vendor..."
            value={keyword}
            onChange={(event) => {
              setKeyword(event.target.value);
              setPage(0);
            }}
          />

          <select
            value={deviceType}
            onChange={(event) => {
              setDeviceType(event.target.value);
              setPage(0);
            }}
          >
            <option value="">All Device Types</option>

            {deviceTypes.map((type) => (
              <option key={type} value={type}>
                {type.replaceAll("_", " ")}
              </option>
            ))}
          </select>

          <select
            value={status}
            onChange={(event) => {
              setStatus(event.target.value);
              setPage(0);
            }}
          >
            <option value="">All Statuses</option>

            {deviceStatuses.map((item) => (
              <option key={item} value={item}>
                {item.replaceAll("_", " ")}
              </option>
            ))}
          </select>

          <input
            type="search"
            placeholder="Filter by vendor"
            value={vendor}
            onChange={(event) => {
              setVendor(event.target.value);
              setPage(0);
            }}
          />

        </div>

        {loading && <p>Loading devices...</p>}

        {error && (
          <p role="alert" className="error-message">
            {error}
          </p>
        )}

        {!loading && !error && (
          <div className="table-wrapper">

            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Hostname</th>
                  <th>IP Address</th>
                  <th>Type</th>
                  <th>Vendor</th>
                  <th>Location</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>

                {devices.map((device) => (
                  <tr key={device.id}>
                    <td>{device.id}</td>
                    <td>{device.hostname}</td>
                    <td>{device.ipAddress}</td>
                    <td>{device.deviceType}</td>
                    <td>{device.vendor ?? "-"}</td>
                    <td>{device.location ?? "-"}</td>
                    <td>
                      <span
                        className={`status-badge ${device.status.toLowerCase()}`}
                      >
                        {device.status.replaceAll("_", " ")}
                      </span>
                    </td>
                    <td>
                      <Link
                        to={`/devices/${device.id}`}
                        className="view-button"
                      >
                        View Details
                      </Link>
                    </td>
                  </tr>
                ))}

                {devices.length === 0 && (
                  <tr>
                    <td colSpan={7}>No devices found.</td>
                  </tr>
                )}

              </tbody>
            </table>

          </div>
        )}

        <div className="pagination">

          <button
            disabled={page === 0 || loading}
            onClick={() => setPage((previous) => previous - 1)}
          >
            Previous
          </button>

          <span>
            Page {totalPages === 0 ? 0 : page + 1} of {totalPages}
          </span>

          <button
            disabled={page + 1 >= totalPages || loading}
            onClick={() => setPage((previous) => previous + 1)}
          >
            Next
          </button>

        </div>

      </div>
    </div>
  );
}
