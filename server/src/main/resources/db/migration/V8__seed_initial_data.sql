-- V8__seed_initial_data.sql
-- Seed initial data for system settings and simulated devices

-- 1. Default System Settings
INSERT INTO system_settings (setting_key, setting_value, category, description, is_encrypted, created_at, updated_at) VALUES
('polling.interval_seconds', '60', 'POLLING', 'Interval in seconds between background device polls', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('polling.ping_timeout_ms', '2000', 'POLLING', 'Timeout in milliseconds for ICMP echo requests', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('polling.ping_retries', '3', 'POLLING', 'Number of consecutive failures required before triggering an incident', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('smtp.host', 'smtp.gmail.com', 'SMTP', 'SMTP server host', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('smtp.port', '587', 'SMTP', 'SMTP server port', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('smtp.user', 'alerts@garudnetwork.local', 'SMTP', 'SMTP username', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('simulator.enabled', 'true', 'SIMULATOR', 'Enable realistic virtual network device engine', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('simulator.device_count', '5', 'SIMULATOR', 'Number of virtual devices to simulate', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 2. 5 Built-in Simulated Network Devices
INSERT INTO devices (id, name, ip_address, mac_address, device_type, status, location, is_simulated, is_enabled, snmp_version, snmp_community, snmp_port, created_at, updated_at) VALUES
(1, 'Edge-Router-01', '192.168.1.1', '00:1A:2B:3C:4D:01', 'ROUTER', 'ONLINE', 'Main Datacenter - Rack A', TRUE, TRUE, 'v2c', 'public', 161, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Core-Switch-01', '192.168.1.2', '00:1A:2B:3C:4D:02', 'SWITCH', 'ONLINE', 'Main Datacenter - Rack A', TRUE, TRUE, 'v2c', 'public', 161, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Distribution-Switch-02', '192.168.1.3', '00:1A:2B:3C:4D:03', 'SWITCH', 'ONLINE', 'Building B - Floor 2', TRUE, TRUE, 'v2c', 'public', 161, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Corporate-Firewall-Primary', '192.168.1.254', '00:1A:2B:3C:4D:04', 'FIREWALL', 'ONLINE', 'Main Datacenter - Perimeter', TRUE, TRUE, 'v2c', 'public', 161, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Access-Point-Floor1', '192.168.1.50', '00:1A:2B:3C:4D:05', 'AP', 'ONLINE', 'Building A - Lobby', TRUE, TRUE, 'v2c', 'public', 161, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 3. Sample Recent Incident for Demonstration
INSERT INTO incidents (id, incident_number, device_id, severity, status, title, trigger_reason, started_at, created_at, updated_at)
VALUES (1, 'INC-1001', 3, 'WARNING', 'RESOLVED', 'Distribution-Switch-02 High Packet Loss', 'Packet loss exceeded threshold (18% > 10%) for 3 consecutive polls', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 4. Sample Incident Event
INSERT INTO incident_events (id, incident_id, event_type, message, performed_by_user_id, created_at, updated_at)
VALUES (1, 1, 'TRIGGERED', 'Incident opened: Device failed 3 consecutive ICMP checks', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'NOTIFICATION_SENT', 'Alert email dispatched to admin@garudnetwork.local', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);