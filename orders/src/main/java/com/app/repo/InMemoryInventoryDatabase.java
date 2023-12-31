package com.app.repo;

import com.app.model.Category;
import com.app.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryInventoryDatabase implements InventoryDatabase {
    private Map<String, Product> products;

    private Map<Category, Integer> categoryStock;

    public InMemoryInventoryDatabase() {
        products = new HashMap<>();
        categoryStock = new HashMap<>();
        for (var cat : Category.values()) {
            categoryStock.put(cat, 0);
        }
    }

    @Override
    public Boolean addProduct(Product product) {
        String serialNo = product.getSerialNumber();
        if (products.containsKey(serialNo)) {
            return false;
        }
        categoryStock.put(product.getCategory(), categoryStock.get(product.getCategory()) + product.getQuantity());
        products.put(serialNo, product);
        return true;
    }

    @Override
    public Boolean increaseProductStock(String serialNot, Integer quantity) {
        if (!products.containsKey(serialNot)) {
            return false;
        }
        categoryStock.put(products.get(serialNot).getCategory(), categoryStock.get(products.get(serialNot).getCategory()) + quantity);
        products.get(serialNot).setQuantity(products.get(serialNot).getQuantity() + quantity);
        return true;
    }

    @Override
    public Boolean decreaseProductStock(String serialNot, Integer quantity) {
        if (!products.containsKey(serialNot) || products.get(serialNot).getQuantity() < quantity) {
            return false;
        }
        categoryStock.put(products.get(serialNot).getCategory(), categoryStock.get(products.get(serialNot).getCategory()) - quantity);
        products.get(serialNot).setQuantity(products.get(serialNot).getQuantity() - quantity);
        return true;
    }


    @Override
    public Product getProduct(String serialNo) {
        return products.get(serialNo);
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Integer getProductStock(String serialNo) {
        if (!products.containsKey(serialNo)) {
            return 0;
        }
        return products.get(serialNo).getQuantity();
    }

    @Override
    public Map<Category, Integer> getCategoriesCount() {
        return categoryStock;
    }
}
