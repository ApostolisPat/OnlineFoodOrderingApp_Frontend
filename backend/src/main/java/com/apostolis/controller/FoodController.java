package com.apostolis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apostolis.model.Food;
import com.apostolis.model.User;
import com.apostolis.service.FoodService;
import com.apostolis.service.UserService;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                            @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserByJwtToken(jwt);
        
        List<Food> foods = foodService.searchFood(name);

        
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam boolean vegeterian,
                                            @RequestParam boolean seasonal,
                                            @RequestParam boolean nonVeg,
                                            @PathVariable Long restaurantId,
                                            @RequestParam(required = false) String food_category,
                                            @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserByJwtToken(jwt);
        
        List<Food> foods = foodService.getRestaurantsFood(restaurantId, vegeterian, nonVeg, seasonal, food_category);

        
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
    

}
