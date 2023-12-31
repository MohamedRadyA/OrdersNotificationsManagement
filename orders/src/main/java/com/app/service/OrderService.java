package com.app.service;

import com.app.model.*;
import com.app.repo.Database;
import org.apache.tomcat.util.bcel.Const;

import java.time.LocalDateTime;

public class OrderService {

    private final int MAXIMUM_CANCEL_DURATION = 2;
    private final Database database;
    private static OrderService instance;

    private OrderService(){
        database = Database.getInstance();
    }
    public static OrderService getInstance(){
        if(instance == null){
            instance = new OrderService();
        }
        return instance;
    }

    boolean createNewOrder(String username) {
        int id = database.getNextOrderId();
        Order newOrder = new Order(id, username, null, null, null, OrderState.IDLE);
        return database.addOrder(newOrder);
    }
    boolean addOrderToOrder(int orderId, int orderToAddId) {
        Order order = database.getOrder(orderId);
        Order orderToAdd = database.getOrder(orderToAddId);
        if (order == null || orderToAdd == null) return false;
        order.addChild(orderToAdd);
        return true;
    }
    boolean addProductToOrder(int orderId, String serialNumber, int quantity) {
        Order order = database.getOrder(orderId);
        if (order == null) return false;
        int stockQuantity = database.getProductStock(serialNumber);
        if (stockQuantity < quantity) return false;
        ProductItem currentProduct = new ProductItem(serialNumber, quantity);
        order.addChild(currentProduct);
        return true;
    }
    boolean cancelOrderPlacement(int orderId) {
        Order order = database.getOrder(orderId);
        if (order == null) return false;
        order.setState(OrderState.IDLE);
        order.setPlacementDate(null);
        // TODO: return the balance
        return true;
    }
    boolean cancelOrderShipping(int orderId) {
        Order order = database.getOrder(orderId);
        if (order == null) return false;
        int hoursPassed = order.getPlacementDate().getHour() - LocalDateTime.now().getHour();
        if (hoursPassed > MAXIMUM_CANCEL_DURATION) return false;
        order.setState(OrderState.PLACED);
        order.setShippingDate(null);
        return true;
    }
    boolean placeOrder(int orderId) {
        Order order = database.getOrder(orderId);
        if (order == null) return false;
        // TODO: Check if the user has enough balance etc.
        order.setState(OrderState.PLACED);
        order.setPlacementDate(LocalDateTime.now());
        return true;
    }
    boolean shipOrder(int orderId) {
        Order order = database.getOrder(orderId);
        if (order == null) return false;
        order.setState(OrderState.SHIPPED);
        order.setShippingDate(LocalDateTime.now());
        return true;
    }
}
