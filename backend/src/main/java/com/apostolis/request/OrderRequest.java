package com.apostolis.request;

import com.apostolis.model.Address;

import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;
    private Address deliveryAddress;

}
