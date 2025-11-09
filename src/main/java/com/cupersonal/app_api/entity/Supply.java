package com.cupersonal.app_api.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.cupersonal.app_api.enums.Unit;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Entity
@Table(name = "supplies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false)
    private Unit unit;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "minimum_quantity", nullable = false)
    private int minimumQuantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Default
    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductSupply> productSupplies = new HashSet<>();

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
