package com.goattech.deliverytracker.model.dto;

import java.time.OffsetDateTime;

public record ErrorResponse(
        String message,
        int status,
        OffsetDateTime timestamp) {
}
