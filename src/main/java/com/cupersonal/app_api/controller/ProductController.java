package com.cupersonal.app_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cupersonal.app_api.dto.request.CreateProductDTO;
import com.cupersonal.app_api.dto.response.ProductResponseDTO;
import com.cupersonal.app_api.dto.update.UpdateProductDTO;

import com.cupersonal.app_api.entity.Product;
import com.cupersonal.app_api.service.ProductService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


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

    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> findAllProducts( 
        @PageableDefault(size = 5, sort = "id") Pageable pageable){
        Page<ProductResponseDTO> products = this.productService.findAllProducts(pageable);
        return ResponseEntity.ok(products.getContent());
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable int id) throws EntityNotFoundException{
        ProductResponseDTO product = this.productService.findProductById(id);
        return new ResponseEntity<ProductResponseDTO>(product, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<Product> findProductAdminById(@PathVariable int id) throws EntityNotFoundException{
        Product product = this.productService.findProductAdminById(id);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }
    

    /*@PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @Valid @RequestBody UpdateProductDTO dto) throws EntityNotFoundException{
        Product product = this.productService.updateProduct(id, dto);
        
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }*/

    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable int id) throws EntityNotFoundException{
        productService.deleteProductById(id);
    }



}
