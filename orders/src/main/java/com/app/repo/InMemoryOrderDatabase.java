package com.app.repo;

import com.app.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class InMemoryOrderDatabase implements OrderDatabase {
    private ArrayList<Order> orders;

    public InMemoryOrderDatabase() {
        orders = new ArrayList<>();
    }

    @Override
    public Boolean addOrder(Order order) {
        orders.add(order);
        return true;
    }

    @Override
    public Order getOrder(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public Integer getNextOrderId() {
        return orders.size() + 1;
    }
}
