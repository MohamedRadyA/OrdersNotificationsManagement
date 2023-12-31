package com.app.service;

import ch.qos.logback.core.joran.sanity.Pair;
import com.app.model.*;
import com.app.repo.Database;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class OrderService {

    private final long MAXIMUM_CANCEL_DURATION = 1 * 24 * 60 * 60;
    private final Database database;


    @Autowired
    public OrderService(@Qualifier("inMemoryDatabase") Database database) {
        this.database = database;
    }

    public Boolean createNewOrder(String username) {
        int id = database.getNextOrderId();
        Order newOrder = new Order(id, username, null, null, null, OrderState.IDLE);
        return database.addOrder(newOrder);
    }

    public Boolean addOrderToOrder(int orderId, int orderToAddId) {
        Order order = database.getOrder(orderId);
        Order orderToAdd = database.getOrder(orderToAddId);
        if (order == null || orderToAdd == null) return false;
        order.addChild(orderToAdd);
        return true;
    }

    public Boolean addProductToOrder(int orderId, String serialNumber, int quantity) {
        if (quantity <= 0) return false;
        Order order = database.getOrder(orderId);
        if (order == null) return false;
        int stockQuantity = database.getProductStock(serialNumber);
        if (stockQuantity < quantity) return false;
        ProductItem currentProduct = new ProductItem(serialNumber, quantity, database.getProduct(serialNumber).getPrice());
        order.addChild(currentProduct);
        return true;
    }

    private void cancelOrderPlacement(Order order) {
        order.setState(OrderState.IDLE);
        order.setPlacementDate(null);
        double price = order.getPrice();
        User user = database.getUser(order.getBuyerUsername());
        user.addBalance(price);
        for (var item : order.getItems()) {
            if (item instanceof Order) {
                cancelOrderPlacement((Order) item);
            }
        }
    }

    public Boolean cancelOrderPlacement(int orderId) {
        Order order = database.getOrder(orderId);
        if (order == null || order.getState() != OrderState.PLACED || !order.getMainOrder()) return false;
        order.setMainOrder(false);
        cancelOrderPlacement(order);
        return true;
    }

    private void cancelOrderShipping(Order order, double singleOrderFees) {
        order.setState(OrderState.PLACED);
        order.setShippingDate(null);

        User user = database.getUser(order.getBuyerUsername());
        user.addBalance(singleOrderFees);
        for (var item : order.getItems()) {
            if (item instanceof Order) {
                cancelOrderShipping((Order) item, singleOrderFees);
            }
        }
    }

    public Boolean cancelOrderShipping(int orderId) {
        Order order = database.getOrder(orderId);
        if (order == null || order.getState() != OrderState.SHIPPED || !order.getMainOrder()) return false;
        Duration duration = Duration.between(order.getPlacementDate(), LocalDateTime.now());
        if (duration.getSeconds() > MAXIMUM_CANCEL_DURATION) return false;

        Double shipmentFees = getShippingFees(order);
        Integer numberOfOrders = getNumberOfOrders(order);
        double singleOrderFees = shipmentFees / numberOfOrders;

        cancelOrderShipping(order, singleOrderFees);
        return true;
    }


    private Boolean verifyUsersBalance(Order order, Function<Order, Double> getPrice) {
        User user = database.getUser(order.getBuyerUsername());
        if (user.getBalance() < getPrice.apply(order)) {
            return false;
        }
        for (var item : order.getItems()) {
            if (item instanceof Order) {
                if (!verifyUsersBalance((Order) item, getPrice)) {
                    return false;
                }
            }
        }
        return true;
    }

    private Boolean verifyOrdersStatus(Order order) {
        if (order.getState().equals(OrderState.SHIPPED))
            return false;
        for (var item : order.getItems()) {
            if (item instanceof Order) {
                if (!verifyOrdersStatus((Order) item)) {
                    return false;
                }
            }
        }
        return true;
    }


    private void placeOrder(Order order) {
        if (order.getState().equals(OrderState.IDLE)) {
            order.setState(OrderState.PLACED);
            order.setPlacementDate(LocalDateTime.now());
            double price = order.getPrice();
            User user = database.getUser(order.getBuyerUsername());
            user.subtractBalance(price);
        }
        for (var item : order.getItems()) {
            if (item instanceof Order) {
                placeOrder((Order) item);
            }
        }
    }

    public Boolean placeOrder(int orderId) {
        Order order = database.getOrder(orderId);
        if (order == null) return false;

        if (!verifyUsersBalance(order, Order::getPrice))
            return false;
        if (!verifyOrdersStatus(order))
            return false;
        order.setMainOrder(true);
        placeOrder(order);
        return true;
    }

    private Double getShippingFees(Order order) {
        return 1000.0;
    }

    private Integer getNumberOfOrders(Order order) {
        Integer cnt = 1;
        for (var item : order.getItems()) {
            if (item instanceof Order) {
                cnt += getNumberOfOrders((Order) item);
            }
        }
        return cnt;
    }

    private void shipOrder(Order order, double singleOrderFees) {
        order.setState(OrderState.SHIPPED);
        order.setShippingDate(LocalDateTime.now());

        User user = database.getUser(order.getBuyerUsername());
        user.subtractBalance(singleOrderFees);
        for (var item : order.getItems()) {
            if (item instanceof Order) {
                shipOrder((Order) item, singleOrderFees);
            }
        }
    }

    public Boolean shipOrder(int orderId) {
        Order order = database.getOrder(orderId);
        if (order == null) return false;

        if (!order.getMainOrder() || !order.getState().equals(OrderState.PLACED))
            return false;

        Double shipmentFees = getShippingFees(order);
        Integer numberOfOrders = getNumberOfOrders(order);
        double singleOrderFees = shipmentFees / numberOfOrders;

        Function<Order, Double> function = x -> singleOrderFees;
        if (!verifyUsersBalance(order, function))
            return false;
        shipOrder(order, singleOrderFees);
        return true;
    }

    public Order getOrderDetails(int orderId) {
        return database.getOrder(orderId);
    }
}
