package com.cupersonal.app_api.service;

import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cupersonal.app_api.dto.request.CreateProductDTO;
import com.cupersonal.app_api.dto.response.ProductResponseDTO;
import com.cupersonal.app_api.dto.update.UpdateProductDTO;
import com.cupersonal.app_api.entity.Product;
import com.cupersonal.app_api.entity.ProductSupply;
import com.cupersonal.app_api.entity.Supply;
import com.cupersonal.app_api.repository.ProductRepository;
import com.cupersonal.app_api.repository.ProductSupplyRepository;
import com.cupersonal.app_api.repository.SupplyRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired 
    private ProductSupplyRepository productSupplyRepository;

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

    public Page<ProductResponseDTO> findAllProducts(Pageable pageable){
        return this.productRepository.findAll(pageable)
            .map(product -> new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl()
            ));
    }

    public ProductResponseDTO findProductById(int id) throws EntityNotFoundException{
        Product product = productRepository.findById(Long.valueOf(id)).orElseThrow(EntityNotFoundException::new);

        return new ProductResponseDTO(Long.valueOf(id), product.getName(), product.getDescription(), product.getPrice(), product.getImageUrl());
    }

    public Product findProductAdminById(int id) throws EntityNotFoundException{
        return this.productRepository.findById(Long.valueOf(id)).orElseThrow(EntityNotFoundException::new);
    }

    /*public Product updateProduct(int id, UpdateProductDTO dto){
        Product product = this.productRepository.findById(Long.valueOf(id)).orElseThrow(EntityNotFoundException::new);
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setImageUrl(dto.imageUrl());
        product.getProductSupplies().clear();
        return productRepository.save(updateProductSupplies(product, dto));
    }

    private Product updateProductSupplies (Product product, UpdateProductDTO dto){
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
    }*/

    public void deleteProductById(int id){
        this.productRepository.deleteById(Long.valueOf(id));
    }
}
