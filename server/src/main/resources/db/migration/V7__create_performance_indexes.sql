-- V7__create_performance_indexes.sql
-- Create additional performance indexes for high-frequency queries

-- Index for device status queries
CREATE INDEX IF NOT EXISTS idx_devices_status ON devices(status) WHERE deleted_at IS NULL;
CREATE INDEX IF NOT EXISTS idx_devices_type ON devices(device_type) WHERE deleted_at IS NULL;
CREATE INDEX IF NOT EXISTS idx_devices_simulated ON devices(is_simulated) WHERE deleted_at IS NULL;

-- Index for device metrics time-series
CREATE INDEX IF NOT EXISTS idx_metrics_recorded_at ON device_metrics(recorded_at DESC);

-- Index for incident severity and status
CREATE INDEX IF NOT EXISTS idx_incidents_severity ON incidents(severity) WHERE deleted_at IS NULL;

-- Index for notification queries
CREATE INDEX IF NOT EXISTS idx_notifications_incident ON notifications(incident_id);
CREATE INDEX IF NOT EXISTS idx_notifications_user ON notifications(user_id);