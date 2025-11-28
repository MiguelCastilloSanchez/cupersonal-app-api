package com.cupersonal.app_api.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(
    Long id,
    String code,
    BigDecimal total,
    String status,
    String email,
    List<OrderProductResponseDTO> products

) {
    public record OrderProductResponseDTO(
        String productName,
        String productImageUrl,
        int quantity
    ) {
    }
}
