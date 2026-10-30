-- V2__create_devices_table.sql
-- Create devices table for network device inventory

CREATE TABLE IF NOT EXISTS devices (
    id BIGINT PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100) NOT NULL,
    ip_address VARCHAR(45) NOT NULL,
    mac_address VARCHAR(17) NULL,
    device_type VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'UNKNOWN',
    location VARCHAR(100) NULL,
    is_simulated BOOLEAN NOT NULL DEFAULT FALSE,
    is_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    snmp_version VARCHAR(10) NULL DEFAULT 'v2c',
    snmp_community VARCHAR(100) NULL DEFAULT 'public',
    snmp_port INT NOT NULL DEFAULT 161,
    consecutive_failures INT NOT NULL DEFAULT 0,
    last_polled_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL
);

-- Indexes for performance
CREATE INDEX IF NOT EXISTS idx_devices_polling ON devices(is_enabled, status) WHERE deleted_at IS NULL;
CREATE UNIQUE INDEX IF NOT EXISTS idx_devices_ip ON devices(ip_address) WHERE deleted_at IS NULL;