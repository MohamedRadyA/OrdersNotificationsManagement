package com.app.service;

import com.app.model.Product;
import com.app.model.notification.NotificationTemplate;
import com.app.model.order.Order;
import com.app.model.order.OrderState;
import com.app.model.order.ProductItem;
import com.app.model.User;
import com.app.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static java.util.Map.entry;

@Service
public class OrderService {

    private final long MAXIMUM_CANCEL_DURATION = 1 * 24 * 60 * 60;

    private final OrderDatabase orderDatabase;

    private final UserDatabase userDatabase;

    private final InventoryDatabase inventoryDatabase;

    private final NotificationService notificationService;


    @Autowired
    public OrderService(@Qualifier("inMemoryOrderDatabase") OrderDatabase orderDatabase,
                        @Qualifier("inMemoryUserDatabase") UserDatabase userDatabase,
                        @Qualifier("inMemoryInventoryDatabase") InventoryDatabase inventoryDatabase,
                        @Qualifier("notificationService") NotificationService notificationService) {
        this.orderDatabase = orderDatabase;
        this.userDatabase = userDatabase;
        this.inventoryDatabase = inventoryDatabase;
        this.notificationService = notificationService;
    }

    public Boolean createNewOrder(String username, String address) {
        Integer id = orderDatabase.getNextOrderId();
        Order newOrder = new Order(id, username, null, null, address, OrderState.IDLE);
        return orderDatabase.addOrder(newOrder);
    }

    public Boolean addOrderToOrder(Integer orderId, Integer orderToAddId) {
        Order order = orderDatabase.getOrder(orderId);
        Order orderToAdd = orderDatabase.getOrder(orderToAddId);
        if (order == null || orderToAdd == null) return false;
        order.addChild(orderToAdd);
        return true;
    }

    public Boolean addProductToOrder(Integer orderId, String serialNumber, Integer quantity) {
        if (quantity <= 0) return false;
        Order order = orderDatabase.getOrder(orderId);
        if (order == null) return false;
        Integer stockQuantity = inventoryDatabase.getProductStock(serialNumber);
        if (stockQuantity < quantity) return false;
        Product product = inventoryDatabase.getProduct(serialNumber);
        product.setQuantity(product.getQuantity() - quantity);
        ProductItem currentProduct = new ProductItem(serialNumber, quantity, inventoryDatabase.getProduct(serialNumber).getPrice());
        order.addChild(currentProduct);
        return true;
    }

    private Boolean verifyUsersBalance(Order order, Function<Order, Double> getPrice) {
        User user = userDatabase.getUser(order.getBuyerUsername());
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
        User user = userDatabase.getUser(order.getBuyerUsername());
        if (order.getState().equals(OrderState.IDLE)) {
            order.setState(OrderState.PLACED);
            order.setPlacementDate(new Date(System.currentTimeMillis()));
            user.subtractBalance(order.getPrice());
        }
        for (var item : order.getItems()) {
            if (item instanceof Order) {
                placeOrder((Order) item);
            }
        }
        Map<String, String> placeHolders = Map.ofEntries(entry("lang", user.getPreferredLang()),
                entry("name", user.getUsername()),
                entry("id", String.valueOf(order.getId())));
        notificationService.sendNotification(NotificationTemplate.ORDER_PLACEMENT, placeHolders, user);
    }

    public Boolean placeOrder(Integer orderId) {
        Order order = orderDatabase.getOrder(orderId);
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
        return 20.0;
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

    private void shipOrder(Order order, Double singleOrderFees) {
        order.setState(OrderState.SHIPPED);
        order.setShippingDate(new Date(System.currentTimeMillis()));

        User user = userDatabase.getUser(order.getBuyerUsername());
        user.subtractBalance(singleOrderFees);
        for (var item : order.getItems()) {
            if (item instanceof Order) {
                shipOrder((Order) item, singleOrderFees);
            }
        }
        Map<String, String> placeHolders = Map.ofEntries(entry("lang", user.getPreferredLang()),
                entry("name", user.getUsername()),
                entry("id", String.valueOf(order.getId())));
        notificationService.sendNotification(NotificationTemplate.ORDER_SHIPMENT, placeHolders, user);

    }

    public Boolean shipOrder(Integer orderId) {
        Order order = orderDatabase.getOrder(orderId);
        if (order == null) return false;

        if (!order.getMainOrder() || !order.getState().equals(OrderState.PLACED))
            return false;

        Double shipmentFees = getShippingFees(order);
        Integer numberOfOrders = getNumberOfOrders(order);
        Double singleOrderFees = shipmentFees / numberOfOrders;

        Function<Order, Double> function = x -> singleOrderFees;
        if (!verifyUsersBalance(order, function))
            return false;
        shipOrder(order, singleOrderFees);
        return true;
    }

    private void cancelOrderPlacement(Order order) {
        order.setState(OrderState.IDLE);
        order.setPlacementDate(null);
        Double price = order.getPrice();
        User user = userDatabase.getUser(order.getBuyerUsername());
        user.addBalance(price);
        for (var item : order.getItems()) {
            if (item instanceof Order) {
                cancelOrderPlacement((Order) item);
            }
        }
    }

    public Boolean cancelOrderPlacement(Integer orderId) {
        Order order = orderDatabase.getOrder(orderId);
        if (order == null || order.getState() != OrderState.PLACED || !order.getMainOrder()) return false;
        order.setMainOrder(false);
        cancelOrderPlacement(order);
        return true;
    }

    private void cancelOrderShipping(Order order, Double singleOrderFees) {
        order.setState(OrderState.PLACED);
        order.setShippingDate(null);

        User user = userDatabase.getUser(order.getBuyerUsername());
        user.addBalance(singleOrderFees);
        for (var item : order.getItems()) {
            if (item instanceof Order) {
                cancelOrderShipping((Order) item, singleOrderFees);
            }
        }
    }

    public Boolean cancelOrderShipping(Integer orderId) {
        Order order = orderDatabase.getOrder(orderId);
        if (order == null || order.getState() != OrderState.SHIPPED || !order.getMainOrder()) return false;
        long duration = (System.currentTimeMillis() - order.getShippingDate().getTime()) / 1000;
        if (duration > MAXIMUM_CANCEL_DURATION) return false;

        Double shipmentFees = getShippingFees(order);
        Integer numberOfOrders = getNumberOfOrders(order);
        Double singleOrderFees = shipmentFees / numberOfOrders;

        cancelOrderShipping(order, singleOrderFees);
        return true;
    }

    public Order getOrderDetails(Integer orderId) {
        return orderDatabase.getOrder(orderId);
    }
}
