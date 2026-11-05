import {
  createBrowserRouter,
  // Navigate,
} from "react-router";

import AppLayout from "../components/layout/AppLayout";
import ProtectedRoute from "../components/common/ProtectedRoute";
import LoginPage from "../features/auth/pages/LoginPage";
import DashboardPage from "../features/dashboard/pages/DashboardPage";
import DevicesPage from "../features/inventory/pages/DevicesPage";
import IPAMPage from "../features/ipam/pages/IPAMPage";
import MonitoringPage from "../features/monitoring/pages/MonitoringPage";
import IncidentsPage from "../features/incidents/pages/IncidentsPage";

export const router = createBrowserRouter([
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
  path: "/",
  element: <ProtectedRoute />,
  children: [
    {
      element: <AppLayout />,
      children: [
        {
          path: "dashboard",
          element: <DashboardPage />,
        },
        {
          path: "devices",
          element: <DevicesPage />,
        },
        {
          path: "ipam",
          element: <IPAMPage />,
        },
        {
          path: "monitoring",
          element: <MonitoringPage />,
        },
        {
          path: "incidents",
          element: <IncidentsPage />,
        },
      ],
    },
  ],
},
]);