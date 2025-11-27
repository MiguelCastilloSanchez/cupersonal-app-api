package com.cupersonal.app_api.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.cupersonal.app_api.dto.request.CreateOrderDTO;
import com.cupersonal.app_api.dto.response.OrderResponseDTO;
import com.cupersonal.app_api.dto.update.UpdateOrderDTO;
import com.cupersonal.app_api.entity.Order;
import com.cupersonal.app_api.service.OrderService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Order createOrder(@Valid @RequestBody CreateOrderDTO dto){
        return orderService.createOrder(dto);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(
        @PageableDefault (size = 5, sort = "id") Pageable pageable) {
        Page<Order> orders = this.orderService.findAllOrders(pageable);
        return ResponseEntity.ok(orders.getContent());
    }

    @GetMapping("/{code}")
    public ResponseEntity<OrderResponseDTO> findOrderAdminByCode(@PathVariable String code) throws EntityNotFoundException{
        OrderResponseDTO order = this.orderService.findOrderByCode(code);
        return new ResponseEntity<OrderResponseDTO>(order, HttpStatus.OK);
    }

    @GetMapping("/admin/{code}")
    public ResponseEntity<Order> findOrderByCode(@PathVariable String code) throws EntityNotFoundException{
        Order order = this.orderService.findOrderAdminByCode(code);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Order> updateOrder(@PathVariable String code, @RequestBody UpdateOrderDTO dto) throws EntityNotFoundException{
        Order order = this.orderService.updateOrder(code, dto);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@PathVariable String code) throws EntityNotFoundException{
        orderService.deleteOrderByCode(code);
    }
    
}
