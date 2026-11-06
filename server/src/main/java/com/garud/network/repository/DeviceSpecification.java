package com.garud.network.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import com.garud.network.model.Device;
import com.garud.network.model.DeviceStatus;
import com.garud.network.model.DeviceType;
import jakarta.persistence.criteria.Predicate;

public final class DeviceSpecification {

    private DeviceSpecification() {}

    public static Specification<Device> filterDevices(
            String keyword,
            DeviceType deviceType,
            DeviceStatus status,
            String vendor,
            String location) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isBlank()) {

                String search = "%" +
                        keyword.trim().toLowerCase() + "%";

                predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get("hostname")),
                                        search),
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get("ipAddress")),
                                        search),
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get("vendor")),
                                        search),
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get("serialNumber")),
                                        search)));
            }

            if (deviceType != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("deviceType"),
                                deviceType));
            }

            if (status != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("status"),
                                status));
            }

            if (vendor != null && !vendor.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("vendor")),
                                "%" + vendor.trim().toLowerCase() + "%"));
            }

            if (location != null && !location.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("location")),
                                "%" + location.trim().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0]));
        };
    }
}
