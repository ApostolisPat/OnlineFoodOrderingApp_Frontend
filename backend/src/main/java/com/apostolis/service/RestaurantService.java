package com.apostolis.service;

import java.util.List;

import com.apostolis.dto.RestaurantDto;
import com.apostolis.model.Restaurant;
import com.apostolis.model.User;
import com.apostolis.request.CreateRestaurantRequest;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    //endpoint for getting all restaurants. Only for admin role
    public List<Restaurant> getAllRestaurant();

    //endpoint for searching a restaurant.
    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavourites(Long restaurantID, User user) throws Exception;

    //Change open or closed status
    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
