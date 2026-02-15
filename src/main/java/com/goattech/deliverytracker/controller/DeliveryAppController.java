package com.goattech.deliverytracker.controller;

import com.goattech.deliverytracker.model.dto.DeliveryAppDto;
import com.goattech.deliverytracker.service.DeliveryAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/delivery-apps")
public class DeliveryAppController {

    private final DeliveryAppService deliveryAppService;

    public DeliveryAppController(DeliveryAppService deliveryAppService) {
        this.deliveryAppService = deliveryAppService;
    }

    @GetMapping
    public List<DeliveryAppDto> getApps(@AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "true") boolean activeOnly) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return activeOnly ? deliveryAppService.getActiveAppsForUser(userId)
                : deliveryAppService.getAllAppsForUser(userId);
    }

    @PostMapping
    public DeliveryAppDto create(@AuthenticationPrincipal Jwt jwt, @RequestBody DeliveryAppDto dto) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return deliveryAppService.createApp(dto, userId);
    }

    @PutMapping("/{id}")
    public DeliveryAppDto update(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID id,
            @RequestBody DeliveryAppDto dto) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return deliveryAppService.updateApp(id, dto, userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID id) {
        UUID userId = UUID.fromString(jwt.getSubject());
        deliveryAppService.deleteApp(id, userId);
        return ResponseEntity.noContent().build();
    }
}
