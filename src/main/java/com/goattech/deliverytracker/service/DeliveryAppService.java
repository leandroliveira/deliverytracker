package com.goattech.deliverytracker.service;

import com.goattech.deliverytracker.exception.BusinessException;
import com.goattech.deliverytracker.model.DeliveryApp;
import com.goattech.deliverytracker.model.dto.DeliveryAppDto;
import com.goattech.deliverytracker.repository.DeliveryAppRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeliveryAppService {

    private final DeliveryAppRepository deliveryAppRepository;

    public DeliveryAppService(DeliveryAppRepository deliveryAppRepository) {
        this.deliveryAppRepository = deliveryAppRepository;
    }

    public List<DeliveryAppDto> getActiveAppsForUser(UUID userId) {
        return deliveryAppRepository.findByUserIdAndActiveTrue(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<DeliveryAppDto> getAllAppsForUser(UUID userId) {
        List<DeliveryApp> apps = deliveryAppRepository.findByUserId(userId);

        if (apps.isEmpty()) {
            throw new BusinessException("No delivery apps found for user");
        }

        return apps.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public DeliveryAppDto createApp(DeliveryAppDto dto, UUID userId) {
        DeliveryApp existing = deliveryAppRepository.findByName(dto.name());

        if (existing != null) {
            throw new BusinessException("Delivery app already exists");
        }

        DeliveryApp app = new DeliveryApp();
        app.setName(dto.name());
        app.setUserId(userId);
        app.setActive(true);
        return toDto(deliveryAppRepository.save(app));
    }

    public DeliveryAppDto updateApp(UUID id, DeliveryAppDto updatedDto, UUID userId) {
        DeliveryApp existing = deliveryAppRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Delivery app not found"));

        if (!existing.getUserId().equals(userId)) {
            throw new BusinessException("Unauthorized to update this delivery app");
        }

        existing.setName(updatedDto.name());
        if (updatedDto.active() != null) {
            existing.setActive(updatedDto.active());
        }

        return toDto(deliveryAppRepository.save(existing));
    }

    public void deleteApp(UUID id, UUID userId) {
        DeliveryApp existing = deliveryAppRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Delivery app not found"));

        if (!existing.getUserId().equals(userId)) {
            throw new BusinessException("Unauthorized to delete this delivery app");
        }

        deliveryAppRepository.delete(existing);
    }

    private DeliveryAppDto toDto(DeliveryApp entity) {
        return new DeliveryAppDto(
                entity.getId(),
                entity.getName(),
                entity.getActive(),
                entity.getCreatedAt());
    }
}
