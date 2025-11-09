package com.cupersonal.app_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "product_supplies")
@Data
@Builder
public class ProductSupply {

    @EmbeddedId
    private ProductSupplyId id = new ProductSupplyId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("supplyId")
    @JoinColumn(name = "supply_id")
    private Supply supply;

    @Column(name = "quantity_per_unit")
    private int quantityPerUnit;
}
