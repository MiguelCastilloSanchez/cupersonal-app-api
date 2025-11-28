package com.cupersonal.app_api.entity;

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
@Table(name = "product_supplies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSupply {

    @Builder.Default
    @EmbeddedId
    private ProductSupplyId id = new ProductSupplyId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("supplyId")
    @JoinColumn(name = "supply_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Supply supply;

    @Column(name = "quantity_per_unit")
    private int quantityPerUnit;
}
