package com.app.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Order implements OrderComponent {
    private ArrayList<OrderComponent> items = new ArrayList<>();
    private int id;
    private String buyerUsername;
    private LocalDateTime placementDate;
    private LocalDateTime shippingDate;
    private String shippingAddress;
    private OrderState state;

    public Order(int id, String buyerUsername, LocalDateTime placementDate, LocalDateTime shippingDate, String shippingAddress, OrderState state) {
        this.id = id;
        this.buyerUsername = buyerUsername;
        this.placementDate = placementDate;
        this.shippingDate = shippingDate;
        this.shippingAddress = shippingAddress;
        this.state = state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }

    public void setPlacementDate(LocalDateTime placementDate) {
        this.placementDate = placementDate;
    }

    public void setShippingDate(LocalDateTime shippingDate) {
        this.shippingDate = shippingDate;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public ArrayList<OrderComponent> getItems() {
        return items;
    }

    public int getId() {
        return id;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }

    public LocalDateTime getPlacementDate() {
        return placementDate;
    }

    public LocalDateTime getShippingDate() {
        return shippingDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public OrderState getState() {
        return state;
    }

    @Override
    public boolean addChild(OrderComponent component) {
        try {
            items.add(component);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeChild(OrderComponent component) {
        if (items.contains(component)) {
            items.remove(component);
            return true;
        }
        return false;
    }

    @Override
    // {["id": id, "buyerUsername": buyerUsername, "placementDate": placementDate, "shippingDate": shippingDate, "items": ["serialNumber": serialNumber, "quantity": quantity]}]]}
    public String getInformation() {
        StringBuilder info = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        info.append("Order {")
                .append("id= ").append(id).append(", ")
                .append("buyerUsername= '").append(buyerUsername).append("', ")
                .append("placementDate= ").append(placementDate.format(formatter)).append(", ")
                .append("shippingDate= ").append(shippingDate.format(formatter)).append(", ")
                .append("shippingAddress= '").append(shippingAddress).append("', ")
                .append("state= ").append(state).append(", ")
                .append("items=[");

        // Separate product items and orders
        ArrayList<OrderComponent> productItems = new ArrayList<>();
        ArrayList<OrderComponent> orders = new ArrayList<>();
        for (OrderComponent item : items) {
            if (item instanceof ProductItem) {
                productItems.add(item);
            } else if (item instanceof Order) {
                orders.add(item);
            }
        }

        // Print product items
        for (int i = 0; i < productItems.size(); i++) {
            info.append("\n ").append(productItems.get(i).getInformation());
            if (i != productItems.size() - 1 || !orders.isEmpty()) {
                info.append(", ");
            }
        }

        // Print orders
        for (int i = 0; i < orders.size(); i++) {
            info.append("\n ").append(orders.get(i).getInformation());
            if (i != orders.size() - 1) {
                info.append(", ");
            }
        }
        if (items.isEmpty())
            info.append("]}");

        return info.toString();
    }
}

