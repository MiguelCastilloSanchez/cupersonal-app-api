package com.cupersonal.app_api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cupersonal.app_api.dto.request.CreateOrderDTO;
import com.cupersonal.app_api.dto.response.OrderResponseDTO;
import com.cupersonal.app_api.dto.update.UpdateOrderDTO;
import com.cupersonal.app_api.entity.Order;
import com.cupersonal.app_api.entity.OrderProduct;
import com.cupersonal.app_api.entity.Product;
import com.cupersonal.app_api.enums.OrderStatus;
import com.cupersonal.app_api.repository.OrderRepository;
import com.cupersonal.app_api.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

    private final EmailService emailService;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    OrderService(EmailService emailService) {
        this.emailService = emailService;
    }


    public Order createOrder(CreateOrderDTO dto){
        Order order = Order.builder()
        .code(this.generateOrderCode())
        .email(dto.email())
        .build();
        return orderRepository.save(createOrderProduct(order, dto));
    }

    private Order createOrderProduct(Order order, CreateOrderDTO dto){
        Set<OrderProduct> orderProducts = dto.products().stream()
        .map(s -> {
            Product product = productRepository.findById(s.id())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));


            OrderProduct op = new OrderProduct();
            op.setOrder(order);
            op.setProduct(product);
            op.setQuantity(s.quantity());
            op.setUnitPrice(product.getPrice());
            op.setProductName(product.getName());
            op.setProductImageUrl(product.getImageUrl());
            return op;
        })
        .collect(Collectors.toSet());
        order.setOrderProducts(orderProducts);
        order.setTotal(this.calculateTotal(order));

        this.sendEmail(order);
        return order;
    }

    private void sendEmail(Order order){
        String email = order.getEmail();
        emailService.sendOrderStatus(email, "Confirmación de orden", "Su orden con código " + order.getCode() + "se actualizó a estado " + order.getStatus());
    }



    private String generateOrderCode(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random r = new Random();
        StringBuilder sb = new StringBuilder("ORD-");

        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }

        return sb.toString();
    }

    private BigDecimal calculateTotal(Order order) {
        BigDecimal total = order.getOrderProducts().stream()
                .map(op -> op.getUnitPrice().multiply(BigDecimal.valueOf(op.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total;
    }

    public Page<Order> findAllOrders(Pageable pageable){
        return this.orderRepository.findAll(pageable);
    } 

    public OrderResponseDTO findOrderByCode(String code) throws EntityNotFoundException{
        Order order = orderRepository.findByCode(code).orElseThrow(EntityNotFoundException::new);
        List<OrderResponseDTO.OrderProductResponseDTO> productDTOs = order.getOrderProducts().stream()
                .map(op -> new OrderResponseDTO.OrderProductResponseDTO(
                    op.getProduct().getName(),
                    op.getProduct().getImageUrl(),
                    op.getQuantity()
                ))
                .toList();

        return new OrderResponseDTO(
            Long.valueOf(order.getId()),
            order.getCode(),
            order.getTotal(),
            order.getStatus().name(),
            order.getEmail(),
            productDTOs
        );
    }
    public Order findOrderAdminByCode(String code) throws EntityNotFoundException{
        return this.orderRepository.findByCode(code).orElseThrow(EntityNotFoundException::new);
    }

    public Order updateOrder(String code, UpdateOrderDTO dto) throws EntityNotFoundException{
        Order order = this.orderRepository.findByCode(code).orElseThrow(EntityNotFoundException::new);
        order.setStatus(OrderStatus.valueOf(dto.status()));
        this.sendEmail(order);
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrderByCode(String code){
        this.orderRepository.deleteByCode(code);
    }






}
