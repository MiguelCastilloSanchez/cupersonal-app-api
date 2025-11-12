package com.cupersonal.app_api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cupersonal.app_api.dto.request.CreateProductDTO;
import com.cupersonal.app_api.entity.Product;
import com.cupersonal.app_api.entity.ProductSupply;
import com.cupersonal.app_api.entity.Supply;
import com.cupersonal.app_api.repository.ProductRepository;
import com.cupersonal.app_api.repository.SupplyRepository;
import com.cupersonal.app_api.service.ProductService;

import jakarta.validation.Valid;

import java.math.BigDecimal;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Product createProduct(@Valid @RequestBody CreateProductDTO dto){
        return productService.createProduct(dto);
    }




}
