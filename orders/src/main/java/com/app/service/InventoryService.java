package com.app.service;

import com.app.model.Product;
import com.app.repo.InventoryDatabase;
import com.app.repo.StatisticsDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class InventoryService {

    private final StatisticsDatabase statisticsDatabase;
    private final InventoryDatabase inventoryDatabase;

    @Autowired
    public InventoryService(@Qualifier("inMemoryStatisticsDatabase") StatisticsDatabase statisticsDatabase,
                            @Qualifier("inMemoryInventoryDatabase") InventoryDatabase inventoryDatabase) {
        this.statisticsDatabase = statisticsDatabase;
        this.inventoryDatabase = inventoryDatabase;
    }

    public ArrayList<Product> getAllProducts() {
        return inventoryDatabase.getAllProducts();
    }

    public Product getProduct(String serialNo){
        return inventoryDatabase.getProduct(serialNo);
    }

    public Boolean addProduct(Product product){
        return inventoryDatabase.addProduct(product);
    }
    public Boolean increaseProductStock(String serialNo, Integer quantity){
        if(quantity < 0)return false;
        return inventoryDatabase.increaseProductStock(serialNo,quantity);
    }

    public Map<String, Integer> getStatistics() {
        return statisticsDatabase.getStatistics();
    }
}
