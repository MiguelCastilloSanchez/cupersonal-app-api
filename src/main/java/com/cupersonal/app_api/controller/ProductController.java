package com.cupersonal.app_api.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cupersonal.app_api.entity.Product;
import com.cupersonal.app_api.repository.ProductRepository;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @PostMapping(value="/products")
    @ResponseStatus(HttpStatus.OK)
    public Product createProduct(){
        Product product = Product.builder()
            .name("Test name")
            .description("Test description")
            .price(new BigDecimal("300.23"))
            .imageUrl("https://mydomain.com/image.png")
            .build();

        return productRepository.save(product);
    }
}
