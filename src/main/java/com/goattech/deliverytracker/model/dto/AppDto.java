package com.goattech.deliverytracker.model.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AppDto(
        UUID id,
        String name,
        Boolean active,
        OffsetDateTime createdAt) {
}
