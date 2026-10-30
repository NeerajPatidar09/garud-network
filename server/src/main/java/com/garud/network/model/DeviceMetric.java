package com.garud.network.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "device_metrics")
@Data
@NoArgsConstructor
@Builder
public class DeviceMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false)
    private Long deviceId;

    @Builder.Default
    @Column(name = "latency_ms", nullable = false)
    private Double latencyMs = 0.0;

    @Builder.Default
    @Column(name = "packet_loss_pct", nullable = false)
    private Double packetLossPct = 0.0;

    @Column(name = "cpu_usage_pct")
    private Double cpuUsagePct;

    @Column(name = "memory_usage_pct")
    private Double memoryUsagePct;

    @Builder.Default
    @Column(name = "bandwidth_in_kbps")
    private Long bandwidthInKbps = 0L;

    @Builder.Default
    @Column(name = "bandwidth_out_kbps")
    private Long bandwidthOutKbps = 0L;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}