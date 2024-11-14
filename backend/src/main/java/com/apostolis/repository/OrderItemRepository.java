package com.apostolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apostolis.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
