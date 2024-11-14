package com.apostolis.service;

import java.util.List;

import com.apostolis.model.Category;

public interface CategoryService {

    //With userId we will find the restaurant, to then create the category for that restaurant
    public Category createCategory(String name, Long userId) throws Exception;

    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception;

    public Category findCategoryById(Long id) throws Exception;

}
