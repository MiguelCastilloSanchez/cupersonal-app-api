package com.cupersonal.app_api.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cupersonal.app_api.entity.Product;
import com.cupersonal.app_api.entity.ProductSupply;
import com.cupersonal.app_api.entity.Supply;
import com.cupersonal.app_api.repository.ProductRepository;
import com.cupersonal.app_api.repository.SupplyRepository;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplyRepository supplyRepository;

    @PostMapping(value="/products")
    @ResponseStatus(HttpStatus.OK)
    public Product createProduct(){
        Optional<Product> product = productRepository.findById(Long.valueOf("1"));

        Optional<Supply> supply = supplyRepository.findById(Long.valueOf("1"));

        ProductSupply productSupply = ProductSupply.builder()
            .product(product.get())
            .supply(supply.get())
            .quantityPerUnit(200)
            .build();
        
        product.get().addProductSupply(productSupply);

        return productRepository.save(product.get());
    }
}
