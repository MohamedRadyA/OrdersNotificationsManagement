package com.app.repo;

import java.util.ArrayList;
import java.util.Map;

import com.app.model.*;
import com.app.notifications.NotificationTemplate;

public class InMemoryDatabase implements Database {

    private Map<String, Product> products;
    private Map<String, User> users;


    private Map<String, Integer> productStock;
    private Map<Category, Integer> categoryStock;
    private ArrayList<Order> orders;
    private Map<String, Integer> emailCounter;
    private Map<NotificationTemplate, Integer> templateCounter;
    private Map<String, Integer> phoneCounter;

    private static InMemoryDatabase database;

    @Override

    public Boolean addOrder(Order order) {
        orders.add(order);
        return true;
    }

    @Override
    public Boolean addProduct(Product product) {
        String serialNo = product.getSerialNumber();
        products.put(serialNo, product);
        productStock.put(serialNo, productStock.getOrDefault(serialNo, 0) + 1);
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
        if (products.containsKey(serialNo)) {
            return products.get(serialNo);
        }
        return null;
    }

    public int getProductStock(String serialNo) {
        return productStock.getOrDefault(productStock.get(serialNo), 0);
    }

    @Override
    public int getNextOrderId() {
        return orders.size() + 1;
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> productArrayList = new ArrayList<Product>(products.values());
        return productArrayList;
    }

    public static Database getInstance() {
        if (database == null)
            database = new InMemoryDatabase();
        return database;
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
