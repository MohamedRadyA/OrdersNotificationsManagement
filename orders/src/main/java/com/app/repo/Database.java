package com.app.repo;

import com.app.model.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;

@Repository
public interface Database {
    Boolean addOrder(Order order);

    Boolean addProduct(Product product);

    Order getOrder(int id);

    Product getProduct(String serialNo);


    Integer getNextOrderId();

    Integer getProductStock(String serialNo);

    ArrayList<Product> getAllProducts();

    Map<String, Integer>getStatistics();


    Boolean userExists(String username);


    void addUser(User user);


    User getUser(String username);

    Boolean updateUser(String username, User user);


}
