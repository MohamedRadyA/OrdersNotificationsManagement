package com.app.service;

import com.app.model.Product;
import com.app.repo.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class InformationService {

    private final Database database;

    @Autowired
    public InformationService(@Qualifier("inMemoryDatabase") Database database) {
        this.database = database;
    }

    public ArrayList<Product> getAllProducts() {
        return database.getAllProducts();
    }

    public Map<String, Integer> getStatistics() {
        return database.getStatistics();
    }
}
