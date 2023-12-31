package com.app.rest;

import com.app.model.Product;
import com.app.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/info")
public class InformationController {

    private InformationService informationService;

    @Autowired
    public InformationController(InformationService informationService) {
        this.informationService = informationService;
    }

    @GetMapping("/getproducts")
    public ResponseEntity<ArrayList<Product>> getProducts() {
        return ResponseEntity.ok(informationService.getAllProducts());
    }

    @GetMapping("/getstats")
    public ResponseEntity<Map<String, Integer>> getStats() {
        return ResponseEntity.ok(informationService.getStatistics());
    }
}
