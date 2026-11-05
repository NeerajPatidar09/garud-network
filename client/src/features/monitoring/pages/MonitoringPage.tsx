
interface MonitoringMetric {
  id: number;
  device: string;
  ipAddress: string;
  cpuUsage: number;
  memoryUsage: number;
  networkUsage: number;
  status: "ONLINE" | "WARNING" | "OFFLINE";
}

const mockMetrics: MonitoringMetric[] = [
  {
    id: 1,
    device: "CORE-RTR-01",
    ipAddress: "192.168.1.1",
    cpuUsage: 35,
    memoryUsage: 62,
    networkUsage: 450,
    status: "ONLINE",
  },
  {
    id: 2,
    device: "SWITCH-FLR-02",
    ipAddress: "192.168.1.2",
    cpuUsage: 78,
    memoryUsage: 85,
    networkUsage: 720,
    status: "WARNING",
  },
  {
    id: 3,
    device: "EDGE-RTR-03",
    ipAddress: "192.168.1.3",
    cpuUsage: 0,
    memoryUsage: 0,
    networkUsage: 0,
    status: "OFFLINE",
  },
];

function getMetricClass(value: number): string {
  if (value >= 80) return "metric-critical";
  if (value >= 60) return "metric-warning";
  return "metric-normal";
}

export default function MonitoringPage() {
  const onlineCount = mockMetrics.filter(
    (metric) => metric.status === "ONLINE"
  ).length;

  const warningCount = mockMetrics.filter(
    (metric) => metric.status === "WARNING"
  ).length;

  const offlineCount = mockMetrics.filter(
    (metric) => metric.status === "OFFLINE"
  ).length;

  return (
    <div className="page-container">
      <div className="page-header">
        <div>
          <h1>Infrastructure Monitoring</h1>
          <p>
            Monitor device health and infrastructure performance.
          </p>
        </div>

        <button className="secondary-button">
          Refresh
        </button>
      </div>

      <div className="stats-grid">
        <div className="stat-card">
          <span>Online Devices</span>
          <h2>{onlineCount}</h2>
        </div>

        <div className="stat-card">
          <span>Warning Devices</span>
          <h2>{warningCount}</h2>
        </div>

        <div className="stat-card">
          <span>Offline Devices</span>
          <h2>{offlineCount}</h2>
        </div>

        <div className="stat-card">
          <span>Total Monitored</span>
          <h2>{mockMetrics.length}</h2>
        </div>
      </div>

      <div className="content-card">
        <div className="table-toolbar">
          <h2>Device Health Overview</h2>

          <span>
            Last updated: {new Date().toLocaleTimeString()}
          </span>
        </div>

        <div className="table-wrapper">
          <table>
            <thead>
              <tr>
                <th>Device</th>
                <th>IP Address</th>
                <th>CPU Usage</th>
                <th>Memory Usage</th>
                <th>Network Usage</th>
                <th>Status</th>
              </tr>
            </thead>

            <tbody>
              {mockMetrics.map((metric) => (
                <tr key={metric.id}>
                  <td>{metric.device}</td>
                  <td>{metric.ipAddress}</td>

                  <td>
                    <div className="metric-cell">
                      <div className="metric-bar">
                        <div
                          className={`metric-fill ${getMetricClass(metric.cpuUsage)}`}
                          style={{
                            width: `${metric.cpuUsage}%`,
                          }}
                        />
                      </div>
                      <span>{metric.cpuUsage}%</span>
                    </div>
                  </td>

                  <td>
                    <div className="metric-cell">
                      <div className="metric-bar">
                        <div
                          className={`metric-fill ${getMetricClass(metric.memoryUsage)}`}
                          style={{
                            width: `${metric.memoryUsage}%`,
                          }}
                        />
                      </div>
                      <span>{metric.memoryUsage}%</span>
                    </div>
                  </td>

                  <td>{metric.networkUsage} Mbps</td>

                  <td>
                    <span
                      className={`status-badge ${metric.status.toLowerCase()}`}
                    >
                      {metric.status}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
