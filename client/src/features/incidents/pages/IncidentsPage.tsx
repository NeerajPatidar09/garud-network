
import { useState } from "react";

interface Incident {
  id: number;
  title: string;
  device: string;
  priority: "CRITICAL" | "HIGH" | "MEDIUM" | "LOW";
  status: "OPEN" | "IN_PROGRESS" | "RESOLVED";
  assignedTo: string;
  createdAt: string;
}

const mockIncidents: Incident[] = [
  {
    id: 1001,
    title: "Core router connectivity failure",
    device: "CORE-RTR-01",
    priority: "CRITICAL",
    status: "OPEN",
    assignedTo: "Admin",
    createdAt: "2026-11-05 09:15",
  },
  {
    id: 1002,
    title: "High packet loss detected",
    device: "SWITCH-FLR-02",
    priority: "HIGH",
    status: "IN_PROGRESS",
    assignedTo: "Network Engineer",
    createdAt: "2026-11-05 10:30",
  },
  {
    id: 1003,
    title: "Interface utilization warning",
    device: "EDGE-RTR-03",
    priority: "MEDIUM",
    status: "RESOLVED",
    assignedTo: "Admin",
    createdAt: "2026-11-04 16:45",
  },
];

export default function IncidentsPage() {
  const [search, setSearch] = useState("");

  const filteredIncidents = mockIncidents.filter(
    (incident) =>
      incident.title.toLowerCase().includes(search.toLowerCase()) ||
      incident.device.toLowerCase().includes(search.toLowerCase())
  );

  const openCount = mockIncidents.filter(
    (incident) => incident.status === "OPEN"
  ).length;

  const inProgressCount = mockIncidents.filter(
    (incident) => incident.status === "IN_PROGRESS"
  ).length;

  const resolvedCount = mockIncidents.filter(
    (incident) => incident.status === "RESOLVED"
  ).length;

  return (
    <div className="page-container">
      <div className="page-header">
        <div>
          <h1>Incident Management</h1>
          <p>
            Monitor, track, and manage network incidents.
          </p>
        </div>

        <button className="primary-button">
          + Create Incident
        </button>
      </div>

      <div className="stats-grid">
        <div className="stat-card">
          <span>Open Incidents</span>
          <h2>{openCount}</h2>
        </div>

        <div className="stat-card">
          <span>In Progress</span>
          <h2>{inProgressCount}</h2>
        </div>

        <div className="stat-card">
          <span>Resolved</span>
          <h2>{resolvedCount}</h2>
        </div>

        <div className="stat-card">
          <span>Total Incidents</span>
          <h2>{mockIncidents.length}</h2>
        </div>
      </div>

      <div className="content-card">
        <div className="table-toolbar">
          <h2>All Incidents</h2>

          <input
            type="search"
            placeholder="Search incidents or devices..."
            value={search}
            onChange={(event) => setSearch(event.target.value)}
          />
        </div>

        <div className="table-wrapper">
          <table>
            <thead>
              <tr>
                <th>Incident ID</th>
                <th>Title</th>
                <th>Device</th>
                <th>Priority</th>
                <th>Status</th>
                <th>Assigned To</th>
                <th>Created At</th>
              </tr>
            </thead>

            <tbody>
              {filteredIncidents.map((incident) => (
                <tr key={incident.id}>
                  <td>INC-{incident.id}</td>
                  <td>{incident.title}</td>
                  <td>{incident.device}</td>
                  <td>
                    <span
                      className={`priority-badge ${incident.priority.toLowerCase()}`}
                    >
                      {incident.priority}
                    </span>
                  </td>
                  <td>
                    <span
                      className={`status-badge ${incident.status.toLowerCase()}`}
                    >
                      {incident.status.replace("_", " ")}
                    </span>
                  </td>
                  <td>{incident.assignedTo}</td>
                  <td>{incident.createdAt}</td>
                </tr>
              ))}

              {filteredIncidents.length === 0 && (
                <tr>
                  <td colSpan={7}>
                    No incidents found.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
