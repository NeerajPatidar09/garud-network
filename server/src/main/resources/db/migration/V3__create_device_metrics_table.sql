-- V3__create_device_metrics_table.sql
-- Create device_metrics table for time-series metric data

CREATE TABLE IF NOT EXISTS device_metrics (
    id BIGINT PRIMARY KEY AUTOINCREMENT,
    device_id BIGINT NOT NULL,
    latency_ms DOUBLE NOT NULL DEFAULT 0.0,
    packet_loss_pct DOUBLE NOT NULL DEFAULT 0.0,
    cpu_usage_pct DOUBLE NULL,
    memory_usage_pct DOUBLE NULL,
    bandwidth_in_kbps BIGINT NULL DEFAULT 0,
    bandwidth_out_kbps BIGINT NULL DEFAULT 0,
    recorded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE
);

-- Index for time-series queries
CREATE INDEX IF NOT EXISTS idx_metrics_device_time ON device_metrics(device_id, recorded_at DESC);