package com.cupersonal.app_api.dto.response;


import com.cupersonal.app_api.enums.Unit;

public record SupplyResponseDTO(
    Long id,
    String name,
    Unit unit,
    int quantity,
    int minimumQuantity
){}
