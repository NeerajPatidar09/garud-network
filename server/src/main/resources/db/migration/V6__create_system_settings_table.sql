-- V6__create_system_settings_table.sql
-- Create system_settings table for application configuration

CREATE TABLE IF NOT EXISTS system_settings (
    id BIGINT PRIMARY KEY AUTOINCREMENT,
    setting_key VARCHAR(100) NOT NULL UNIQUE,
    setting_value TEXT NOT NULL,
    category VARCHAR(50) NOT NULL DEFAULT 'GENERAL',
    description VARCHAR(255) NULL,
    is_encrypted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Unique index for settings key
CREATE UNIQUE INDEX IF NOT EXISTS idx_settings_key ON system_settings(setting_key);