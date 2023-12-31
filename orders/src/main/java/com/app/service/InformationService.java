package com.app.service;

import com.app.model.Product;
import com.app.repo.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InformationService {

    private final Database database;
    private static InformationService instance;


    private InformationService(){
        database = Database.getInstance();
    }
    public static InformationService getInstance(){
        if(instance == null){
            instance = new InformationService();
        }
        return instance;
    }

    public ArrayList<Product> getAllProducts(){
        return database.getAllProducts();
    }

    public HashMap<String, Integer>getStatistics(){
        return database.getStatistics();
    }
}
