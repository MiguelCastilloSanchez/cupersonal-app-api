package com.cupersonal.app_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cupersonal.app_api.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    Optional<Order> findByCode(String code);

    void deleteByCode(String code);
}
