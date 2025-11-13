package com.cupersonal.app_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cupersonal.app_api.entity.ProductSupply;
import com.cupersonal.app_api.entity.ProductSupplyId;

@Repository
public interface ProductSupplyRepository extends JpaRepository<ProductSupply,ProductSupplyId> {

    

    
}
