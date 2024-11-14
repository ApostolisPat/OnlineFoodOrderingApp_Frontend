package com.apostolis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apostolis.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

    //'%'=wildcard. Query allows for users to find restaurants based on partial matches
    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%',:query,'%')) "+
            "OR lower(r.cuisineType) LIKE lower(concat('%',:query,'%'))")
    List<Restaurant> findBySearchQuery(String query);

    Restaurant findByOwnerId(Long userId);


}
