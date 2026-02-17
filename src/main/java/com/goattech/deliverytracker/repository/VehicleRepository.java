package com.goattech.deliverytracker.repository;

import com.goattech.deliverytracker.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    Optional<Vehicle> findByNameAndUserId(String name, UUID userId);

    Optional<Vehicle> findByPlateNumberAndUserId(String plateNumber, UUID userId);

    List<Vehicle> findByUserId(UUID userId);
}
