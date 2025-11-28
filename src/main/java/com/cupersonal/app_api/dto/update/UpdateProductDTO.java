package com.cupersonal.app_api.dto.update;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
public record UpdateProductDTO (
    @NotNull
    BigDecimal price
) {
}
