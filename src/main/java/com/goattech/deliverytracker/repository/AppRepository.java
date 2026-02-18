package com.goattech.deliverytracker.repository;

import com.goattech.deliverytracker.model.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppRepository extends JpaRepository<App, UUID> {
    List<App> findByUserId(UUID userId);

    List<App> findByUserIdAndActiveTrue(UUID userId);
}
