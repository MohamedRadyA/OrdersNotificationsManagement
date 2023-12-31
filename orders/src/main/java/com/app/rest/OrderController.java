package com.app.rest;

import com.app.model.Order;
import com.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create/")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok("Order created");
    }

    @PostMapping("/addproduct/")
    public boolean addProductToOrder() {
        return true;
    }

    @PostMapping("/addorder/")
    public boolean addOrderToOrder() {
        return true;
    }

    @GetMapping("/getorder/")
    public String getOrder(@PathVariable Integer id) {
        return "Get order";
    }

    @PostMapping("/cancelorderplacement/")
    public boolean cancelOrderPlacement() {
        return true;
    }

    @PostMapping("/cancelordershipping/")
    public boolean cancelOrderShipping() {
        return true;
    }

    @PutMapping("/placeorder/")
    public boolean placeOrder() {
        return true;
    }

    @PutMapping("/shiporder/")
    public boolean shipOrder() {
        return true;
    }




}
