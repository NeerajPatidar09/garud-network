-- V4__create_incidents_and_events_tables.sql
-- Create incidents and incident_events tables for incident tracking

CREATE TABLE IF NOT EXISTS incidents (
    id BIGINT PRIMARY KEY AUTOINCREMENT,
    incident_number VARCHAR(20) NOT NULL UNIQUE,
    device_id BIGINT NOT NULL,
    severity VARCHAR(20) NOT NULL DEFAULT 'WARNING',
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN',
    title VARCHAR(200) NOT NULL,
    trigger_reason TEXT NOT NULL,
    started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    acknowledged_at TIMESTAMP NULL,
    acknowledged_by_user_id BIGINT NULL,
    resolved_at TIMESTAMP NULL,
    resolved_by_user_id BIGINT NULL,
    duration_minutes INT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE,
    FOREIGN KEY (acknowledged_by_user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (resolved_by_user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS incident_events (
    id BIGINT PRIMARY KEY AUTOINCREMENT,
    incident_id BIGINT NOT NULL,
    event_type VARCHAR(30) NOT NULL,
    message TEXT NOT NULL,
    performed_by_user_id BIGINT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (incident_id) REFERENCES incidents(id) ON DELETE CASCADE,
    FOREIGN KEY (performed_by_user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Indexes for incident queries
CREATE INDEX IF NOT EXISTS idx_incidents_active ON incidents(status, severity) WHERE deleted_at IS NULL;
CREATE INDEX IF NOT EXISTS idx_incidents_device ON incidents(device_id, status);
CREATE INDEX IF NOT EXISTS idx_incident_events_incident ON incident_events(incident_id);