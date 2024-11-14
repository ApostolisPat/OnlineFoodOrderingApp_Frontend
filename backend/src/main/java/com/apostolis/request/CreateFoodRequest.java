package com.apostolis.request;

import java.util.List;

import com.apostolis.model.IngredientsItem;
import com.apostolis.model.Category;

import lombok.Data;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;
    
    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredients;

}



/*
 * Example JSON Body
 *
{
    "name" : "Margarita Pizza",
    "description" : "Pizza with red tomato sauce, white mozzarella and fresh green basil",
    "price" : 5.5,
    "category" : {
        "name": "Pizza"
    },
    "images" : [
        "https://cdn.pixabay.com/photo/2015/01/16/15/01/dinner-601576_640.jpg",
        "http://res.cloudinary.com.dcpesbd8q/image/upload/v1707801951/tqxhaxr5abm7m0v1mskv.jpg"
    ],
    "restaurantId" : 3,
    "vegeterian" : false,
    "seasonal" : false,
    "ingredients": [{
        "name" : "Dough",
        "category" 
    }]



}
 */
