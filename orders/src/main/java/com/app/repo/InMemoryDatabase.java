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
    private Map<String, Integer> addressCounter;
    private Map<NotificationTemplate, Integer> templateCounter;

    public InMemoryDatabase() {
        products = new HashMap<>();
        users = new HashMap<>();
        categoryStock = new HashMap<>();
        orders = new ArrayList<>();
        addressCounter = new HashMap<>();
        templateCounter = new HashMap<>();
    }

    @Override

    public Boolean addOrder(Order order) {
        orders.add(order);
        return true;
    }

    // Update stock
    @Override
    public Boolean increaseProductStock(String serialNot, Integer quantity) {
        if (products.containsKey(serialNot)) {
            return false;
        }
        categoryStock.put(products.get(serialNot).getCategory(), categoryStock.get(products.get(serialNot).getCategory()) + quantity);
        products.get(serialNot).setQuantity(products.get(serialNot).getQuantity() + quantity);
        return true;
    }

    @Override
    public Boolean decreaseProductStock(String serialNot, Integer quantity) {
        if (products.containsKey(serialNot) || products.get(serialNot).getQuantity() < quantity) {
            return false;
        }
        categoryStock.put(products.get(serialNot).getCategory(), categoryStock.get(products.get(serialNot).getCategory()) - quantity);
        products.get(serialNot).setQuantity(products.get(serialNot).getQuantity() - quantity);
        return true;
    }

    @Override
    public Boolean addProduct(Product product) {
        String serialNo = product.getSerialNumber();
        if (products.containsKey(serialNo)) {
            return false;
        }
        categoryStock.put(product.getCategory(),categoryStock.get(product.getCategory()) + product.getQuantity());
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

    public Integer getProductStock(String serialNo) {
        if (products.containsKey(serialNo)) {
            return 0;
        }
        return products.get(serialNo).getQuantity();
    }

    @Override
    public Integer getNextOrderId() {
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
        Map<String, Integer> statistics = new HashMap<>();
        String mostNotifiedAddress = null;
        NotificationTemplate mostSentTemplate = null;
        int mostNotifiedAddressCount = 0, mostSentTemplateCount = 0;

        for (Map.Entry<String, Integer> entry : addressCounter.entrySet()) {
            if (entry.getValue() > mostNotifiedAddressCount) {
                mostNotifiedAddressCount = entry.getValue();
                mostNotifiedAddress = entry.getKey();
            }
        }

        for (Map.Entry<NotificationTemplate, Integer> entry : templateCounter.entrySet()) {
            if (entry.getValue() > mostSentTemplateCount) {
                mostSentTemplateCount = entry.getValue();
                mostSentTemplate = entry.getKey();
            }
        }
        if (mostNotifiedAddress != null)
            statistics.put("mostNotifiedAddress: " + mostNotifiedAddress, mostNotifiedAddressCount);
        if (mostSentTemplate != null)
            statistics.put("mostSentTemplate: " + mostSentTemplate, mostSentTemplateCount);
        return statistics;
    }
}
