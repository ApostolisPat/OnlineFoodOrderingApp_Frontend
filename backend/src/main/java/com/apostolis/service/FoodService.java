package com.apostolis.service;

import java.util.List;

import com.apostolis.model.Category;
import com.apostolis.model.Food;
import com.apostolis.model.Restaurant;
import com.apostolis.request.CreateFoodRequest;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                            boolean isVegeterian,
                                            boolean isNonVeg,
                                            boolean isSeasonal,
                                            String foodCategory);

    
    public List<Food> searchFood(String keyword);
    
    public Food findFoodById(Long foodId) throws Exception;

    //If food out of stock
    public Food updateAvailabilityStatus(Long foodId) throws Exception;

}
