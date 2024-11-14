package com.apostolis.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apostolis.dto.RestaurantDto;
import com.apostolis.model.Address;
import com.apostolis.model.Restaurant;
import com.apostolis.model.User;
import com.apostolis.repository.AddressRepository;
import com.apostolis.repository.RestaurantRepository;
import com.apostolis.repository.UserRepository;
import com.apostolis.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        
        Address address = addressRepository.save(req.getAddress()); //Save address
        
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
        
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
        
        Restaurant restaurant = findRestaurantById(restaurantId);
        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if(restaurant.getDescription()!=null){
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if(restaurant.getName() != null){
            restaurant.setName(updatedRestaurant.getName());
        }

        //Could add more functionality here

        return restaurantRepository.save(restaurant); //Does this create new restaurant or update?
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        
        Restaurant restaurant = findRestaurantById(restaurantId); //Will throw exception if it doesn't find it

        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        
        Optional<Restaurant> opt = restaurantRepository.findById(id);

        if(opt.isEmpty()){
            throw new Exception("restaurant with id: " + id + " not found.");
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if(restaurant==null){
            throw new Exception("No restaurant found with owner id: " + userId);
        }
        
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavourites(Long restaurantID, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantID);
        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantID);

        boolean isFavourited = false;
        List<RestaurantDto> favourites = user.getFavorites();
        for (RestaurantDto favourite : favourites){
            if(favourite.getId().equals(restaurantID)){
                isFavourited = true;
                break;
            }
        }

        if(isFavourited){
            favourites.removeIf(favourite -> favourite.getId().equals(restaurantID));
        }else{
            favourites.add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);

    }

}
