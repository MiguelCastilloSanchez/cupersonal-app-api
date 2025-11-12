package com.cupersonal.app_api.service;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cupersonal.app_api.dto.request.CreateProductDTO;
import com.cupersonal.app_api.entity.Product;
import com.cupersonal.app_api.entity.ProductSupply;
import com.cupersonal.app_api.entity.Supply;
import com.cupersonal.app_api.repository.ProductRepository;
import com.cupersonal.app_api.repository.SupplyRepository;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplyRepository supplyRepository;

    public Product createProduct(CreateProductDTO dto){
        Product product = Product.builder()
       .name(dto.name())
       .description(dto.description())
       .price((dto.price()))
       .imageUrl(dto.imageUrl())
       .build();
        
       return productRepository.save(createProductSupply(product, dto));
    }

    private Product createProductSupply(Product product, CreateProductDTO dto){
        Set<ProductSupply> productSupplies = dto.supplies().stream()
        .map(s -> {
            Supply supply = supplyRepository.findById(s.id())
                .orElseThrow(() -> new RuntimeException("Supply not found: " + s.id()));

            ProductSupply ps = new ProductSupply();
            ps.setProduct(product);
            ps.setSupply(supply);
            ps.setQuantityPerUnit(s.quantityPerUnit());

            return ps;
        })
        .collect(Collectors.toSet());
        product.setProductSupplies(productSupplies);
        return product;
    }
}
