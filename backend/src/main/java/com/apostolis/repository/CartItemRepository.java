package com.apostolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apostolis.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
