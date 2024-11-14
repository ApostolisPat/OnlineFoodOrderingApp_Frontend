package com.apostolis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apostolis.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findByCustomerId(Long userId); //Returns all Orders with this userId
    public List<Order> findByRestaurantId(Long restaurantId); //Returns all Orders with this restaurantId

}
