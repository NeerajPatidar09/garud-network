import { NavLink } from "react-router";

const navigationItems = [
  { label: "Dashboard", path: "/dashboard" },
  { label: "Devices", path: "/devices" },
  { label: "IP Address Management", path: "/ipam" },
  { label: "Monitoring", path: "/monitoring" },
  { label: "Incidents", path: "/incidents" },
];

export default function Sidebar() {
  return (
    <aside className="sidebar">

      <div className="sidebar-brand">
        <h2>NOIMP</h2>
        <span>Infrastructure Platform</span>
      </div>

      <nav className="sidebar-navigation">

        {navigationItems.map((item) => (
          <NavLink
            key={item.path}
            to={item.path}
            className={({ isActive }) =>
              isActive ? "nav-link active" : "nav-link"
            }
          >
            {item.label}
          </NavLink>
        ))}

      </nav>

    </aside>
  );
}