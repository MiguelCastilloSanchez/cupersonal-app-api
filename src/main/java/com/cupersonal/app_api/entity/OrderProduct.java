package com.cupersonal.app_api.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "order_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProduct {

    @Builder.Default
    @EmbeddedId
    private OrderProductId id = new OrderProductId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Product product;


    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_image_url")
    private String productImageUrl;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;
}
