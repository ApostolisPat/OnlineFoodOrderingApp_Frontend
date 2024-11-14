package com.apostolis.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apostolis.model.Address;
import com.apostolis.model.Cart;
import com.apostolis.model.CartItem;
import com.apostolis.model.Order;
import com.apostolis.model.OrderItem;
import com.apostolis.model.Restaurant;
import com.apostolis.model.User;
import com.apostolis.repository.AddressRepository;
import com.apostolis.repository.OrderItemRepository;
import com.apostolis.repository.OrderRepository;
import com.apostolis.repository.UserRepository;
import com.apostolis.request.OrderRequest;

@Service
public class OrderServiceImpl implements OrderService{


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {
        
        Address shippingAddress = order.getDeliveryAddress();
        
        Address savedAddress = addressRepository.save(shippingAddress);

        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());

        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date(0));
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem cartItem : cart.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        Long totalPrice = cartService.calculateCartTotals(cart);

        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(createdOrder);

        restaurant.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {

        Order order = findOrderById(orderId);

        if(orderStatus.equals("OUT_FOR_DELIVERY")
            ||orderStatus.equals("DELIVERED")
            ||orderStatus.equals("COMPLETED")
            ||orderStatus.equals("PENDING")
            ){
                order.setOrderStatus(orderStatus);
                return orderRepository.save(order);
            }
        throw new Exception("Please select a valid order status.");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        
        Order order = findOrderById(orderId);

        orderRepository.deleteById(orderId);

    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        
        return orderRepository.findByCustomerId(userId);

    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        //If orderStatus is null (User doesn't want to filter by status of order), then the method
        // returns all orders

        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus != null){
            orders = orders.stream().filter(order -> 
                    order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }

        return orders;

    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isEmpty()){
            throw new Exception("Order not found.");
        }
        
        return optionalOrder.get();
    }

}
