package com.app.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.app.model.*;
import com.app.notifications.NotificationTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryDatabase implements Database {

    private Map<String, Product> products;
    private Map<String, User> users;

    private Map<Category, Integer> categoryStock;
    private ArrayList<Order> orders;
    private Map<String, Integer> emailCounter;
    private Map<NotificationTemplate, Integer> templateCounter;
    private Map<String, Integer> phoneCounter;

    public InMemoryDatabase() {
        products = new HashMap<>();
        users = new HashMap<>();
        categoryStock = new HashMap<>();
        orders = new ArrayList<>();
        emailCounter = new HashMap<>();
        templateCounter = new HashMap<>();
        phoneCounter = new HashMap<>();
    }

    @Override

    public Boolean addOrder(Order order) {
        orders.add(order);
        return true;
    }

    // Update stock
//    @Override
//    public Boolean addProduct(Product product) {
//        String serialNo = product.getSerialNumber();
//        products.put(serialNo, product);
//        productStock.put(serialNo, productStock.getOrDefault(serialNo, 0) + 1);
//        return true;
//    }

        @Override
    public Boolean addProduct(Product product) {
        String serialNo = product.getSerialNumber();
        if(products.containsKey(serialNo)){
            return false;
        }
        products.put(serialNo, product);
        return true;
    }

    @Override
    public Order getOrder(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public Product getProduct(String serialNo) {
        return products.get(serialNo);
    }

    public int getProductStock(String serialNo) {
        if(products.containsKey(serialNo)){
            return 0;
        }
        return products.get(serialNo).getQuantity();
    }

    @Override
    public int getNextOrderId() {
        return orders.size() + 1;
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Boolean userExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public User getUser(String username) {
        return users.get(username);
    }

    public Boolean updateUser(String username, User user) {
        users.put(username, user);
        return true;
    }

    public Map<String, Integer> getStatistics() {
        return null;
    }
}
