package com.cupersonal.app_api.dto.request;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductDTO(
    @NotBlank
    @Size(min = 2, max = 50)
    String name,

    @NotBlank
    @Size(min = 2, max = 1000)
    String description,

    @NotNull
    BigDecimal price,

    String imageUrl,

    @NotNull
    List<SupplyQuantityDto> supplies

) {
    public record SupplyQuantityDto(
        @NotNull
        Long id,

        @Min(1)
        int quantityPerUnit
    ) {}
}
