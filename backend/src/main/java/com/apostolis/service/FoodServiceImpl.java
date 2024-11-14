package com.apostolis.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apostolis.model.Category;
import com.apostolis.model.Food;
import com.apostolis.model.Restaurant;
import com.apostolis.repository.FoodRepository;
import com.apostolis.request.CreateFoodRequest;


@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepository foodRepository; //to save,update and delete foods from db

    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSeasonal());
        food.setVegeterian(req.isVegetarian());

        Food savedFood =  foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
    
        //The restaurant property of this food will be set to null
        //Instead of deleting the food itself
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId,
                                        boolean isVegeterian, 
                                        boolean isNonVeg, 
                                        boolean isSeasonal,
                                        String foodCategory) {

            List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

            if(isVegeterian){
                foods = filterByVegeterian(foods, isVegeterian);
            }

            if(isNonVeg){
                foods = filterByNonVeg(foods, isNonVeg);
            }

            if(isSeasonal){
                foods = filterBySeasonal(foods, isSeasonal);
            }

            if(foodCategory!=null && !foodCategory.equals("")){
                foods = filterByCategory(foods, foodCategory);
            }

            return foods;
        }

    private List<Food> filterByVegeterian(List<Food> foods, boolean isVegeterian){
        
        return foods.stream().filter(food -> food.isVegeterian() == isVegeterian).collect(Collectors.toList());
    
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg){
        
        return foods.stream().filter(food -> food.isVegeterian() == false).collect(Collectors.toList());
    
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal){
        
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory){
        
        return foods.stream().filter(food -> {
            if(food.getFoodCategory() != null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    
    }

    @Override
    public List<Food> searchFood(String keyword) {
        
        return foodRepository.searchFood(keyword);

    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        
        if(optionalFood.isEmpty()){
            throw new Exception("food doesn't exist.");
        }

        return optionalFood.get();
    
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        
        Food food = findFoodById(foodId);

        food.setAvailable(!food.isAvailable());

        return foodRepository.save(food);
    
    }

    

}
