package com.app.rest;

import com.app.model.Order;
import com.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //TODO
    @PostMapping("/create")
    public ResponseEntity<String> createNewOrder(@RequestBody Map<String, Object> data) {
        String username = (String)data.get("username");
        String address = (String)data.get("address");
        orderService.createNewOrder(username,address);
        return ResponseEntity.ok("Order created");
    }

    @PostMapping("/addproduct")
    public ResponseEntity<String> addProductToOrder(@RequestBody Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        String serialNumber = (String) data.get("serialNumber");
        Integer quantity = (Integer) data.get("quantity");
        if (!orderService.addProductToOrder(id, serialNumber, quantity)) {
            return ResponseEntity.badRequest().body("Product not added");
        }
        return ResponseEntity.ok("Product added");
    }

    @PostMapping("/addorder")
    public ResponseEntity<String> addOrderToOrder(@RequestBody Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        Integer orderToAddId = (Integer) data.get("orderToAddId");
        if (!orderService.addOrderToOrder(id, orderToAddId)) {
            return ResponseEntity.badRequest().body("Order not added");
        }
        return ResponseEntity.ok("Order added");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Integer id) {
        Order order = orderService.getOrderDetails(id);
        if (order == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(order);
    }

    @PostMapping("/cancelplacement")
    public ResponseEntity<String> cancelOrderPlacement(@RequestBody Integer id) {
        if (!orderService.cancelOrderPlacement(id)) {
            return ResponseEntity.badRequest().body("Order not cancelled");
        }
        return ResponseEntity.ok("Order cancelled");
    }

    @PostMapping("/cancelshipping")
    public ResponseEntity<String> cancelOrderShipping(@RequestBody Integer id) {
        if (!orderService.cancelOrderShipping(id)) {
            return ResponseEntity.badRequest().body("Order not cancelled");
        }
        return ResponseEntity.ok("Order cancelled");
    }

    @PutMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody Integer id) {
        if (!orderService.placeOrder(id)) {
            return ResponseEntity.badRequest().body("Order not placed");
        }
        return ResponseEntity.ok("Order placed");
    }

    @PutMapping("/ship")
    public ResponseEntity<String> shipOrder(@RequestBody Integer id) {
        if (!orderService.shipOrder(id)) {
            return ResponseEntity.badRequest().body("Order not shipped");
        }
        return ResponseEntity.ok("Order shipped");
    }
}