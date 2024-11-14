package com.apostolis.request;

import java.util.List;

import com.apostolis.model.Address;
import com.apostolis.model.ContactInformation;

import lombok.Data;

@Data
public class CreateRestaurantRequest {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;

}


/* Example JSON body
 * {
    "name" : "Food2",
    "description" : "Description of the restaurant2",
    "cuisineType" : "Italian2",
    "address": {
        "streetAddress" : "321 Main Street2",
        "city" : "Athens2",
        "stateProvince" : "Proskopi2",
        "postalCode" : "453212",
        "country" : "Greece2"
    },
    "contactInformation" : {
        "email" : "apostolis.patrikios@gmail.com2",
        "mobile" : "123-456-78902",
        "x" : "@restaurant-formerlytwitter2",
        "instagram" : "@restaurant_insta2"
    },
    "openingHours" : "Mon-Sun: 9:00 AM - 9:00 PM",
    "images": [
        "https://cdn.pixabay.com/photo/2015/01/16/15/01/dinner-601576_640.jpg",
        "http://res.cloudinary.com.dcpesbd8q/image/upload/v1707801951/tqxhaxr5abm7m0v1mskv.jpg"]
}
 */