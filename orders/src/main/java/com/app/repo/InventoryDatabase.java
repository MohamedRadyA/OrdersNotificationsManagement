package com.app.repo;

import com.app.model.Category;
import com.app.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;

@Repository
public interface InventoryDatabase {

    Boolean addProduct(Product product);

    public Boolean increaseProductStock(String serialNo,Integer quantity);

    public Boolean decreaseProductStock(String serialNo,Integer quantity);

    Product getProduct(String serialNo);

    ArrayList<Product> getAllProducts();

    Integer getProductStock(String serialNo);

    Map<Category, Integer> getCategoriesCount();
}
