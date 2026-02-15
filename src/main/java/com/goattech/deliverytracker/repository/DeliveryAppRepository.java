package com.goattech.deliverytracker.repository;

import com.goattech.deliverytracker.model.DeliveryApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryAppRepository extends JpaRepository<DeliveryApp, UUID> {

    DeliveryApp findByName(String name);

    List<DeliveryApp> findByUserIdAndActiveTrue(UUID userId);

    List<DeliveryApp> findByUserId(UUID userId);
}
