package com.app.rest;

import com.app.model.Product;
import com.app.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/getall")
    public ResponseEntity<ArrayList<Product>> getProducts() {
        return ResponseEntity.ok(inventoryService.getAllProducts());
    }

    @GetMapping("/get/{serialNo}")
    public ResponseEntity<Product> getProduct(@PathVariable String serialNo) {
        Product product = inventoryService.getProduct(serialNo);
        if (product == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        if(!inventoryService.addProduct(product)){
            return ResponseEntity.badRequest().body("Product not added");
        }
        return ResponseEntity.ok("Product added");
    }

    @PutMapping("/increase")
    public ResponseEntity<String> increaseProductStock(@RequestBody Map<String, Object> data) {
        String serialNo = (String) data.get("serialNo");
        Integer quantity = (Integer) data.get("quantity");
        if (!inventoryService.increaseProductStock(serialNo,quantity)) {
            return ResponseEntity.badRequest().body("Stock not increased");
        }
        return ResponseEntity.ok("Stock increased");
    }

    @GetMapping("/getstats")
    public ResponseEntity<Map<String, Integer>> getStats() {
        return ResponseEntity.ok(inventoryService.getStatistics());
    }
}
