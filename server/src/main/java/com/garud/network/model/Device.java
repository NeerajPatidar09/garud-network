package com.garud.network.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "devices")
@Data
@NoArgsConstructor
@Builder
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "ip_address", nullable = false, length = 45)
    private String ipAddress;

    @Column(name = "mac_address", length = 17)
    private String macAddress;

    @Column(name = "device_type", nullable = false, length = 30)
    private String deviceType;

    @Builder.Default
    @Column(name = "status", nullable = false, length = 20)
    private String status = "UNKNOWN";

    @Column(name = "location", length = 100)
    private String location;

    @Builder.Default
    @Column(name = "is_simulated", nullable = false)
    private Boolean isSimulated = false;

    @Builder.Default
    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;

    @Builder.Default
    @Column(name = "snmp_version", length = 10)
    private String snmpVersion = "v2c";

    @Builder.Default
    @Column(name = "snmp_community", length = 100)
    private String snmpCommunity = "public";

    @Builder.Default
    @Column(name = "snmp_port", nullable = false)
    private Integer snmpPort = 161;

    @Builder.Default
    @Column(name = "consecutive_failures", nullable = false)
    private Integer consecutiveFailures = 0;

    @Column(name = "last_polled_at")
    private Instant lastPolledAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}