package com.cupersonal.app_api.dto.request;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrderDTO(

    @NotBlank
    String email,

    @NotNull
    List<OrderProductDTO> products
)
{
    public record OrderProductDTO(
        @NotNull
        Long id,

        @Min(1)
        int quantity
    ) {}
}
