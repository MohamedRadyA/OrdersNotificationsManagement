package com.app.repo;


import com.app.model.order.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDatabase {

    Boolean addOrder(Order order);

    Order getOrder(int id);

    Integer getNextOrderId();
}
