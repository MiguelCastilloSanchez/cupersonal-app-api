package com.cupersonal.app_api.dto.request;

import com.cupersonal.app_api.enums.Unit;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateSupplyDTO( 
    @NotBlank
    @Size(min = 2, max = 50)
    String name,

    @NotNull
    Unit unit,

    @Min(0)
    int quantity,

    @Min(0)
    int minimumQuantity
) {}
