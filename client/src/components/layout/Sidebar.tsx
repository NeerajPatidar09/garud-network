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
        <img
          src="/images/garud-network-logo.png"
          alt="Garud Network"
          className="sidebar-logo"
        />
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