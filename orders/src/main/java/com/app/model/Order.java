package com.app.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Order implements OrderComponent {
    private ArrayList<OrderComponent> items;
    private int id;
    private String buyerUsername;
    private LocalDateTime placementDate;
    private LocalDateTime shippingDate;
    private String shippingAddress;
    private OrderState state;

    private Boolean isMainOrder;

    public Order(int id, String buyerUsername, LocalDateTime placementDate, LocalDateTime shippingDate, String shippingAddress, OrderState state) {
        items = new ArrayList<>();
        this.id = id;
        this.buyerUsername = buyerUsername;
        this.placementDate = placementDate;
        this.shippingDate = shippingDate;
        this.shippingAddress = shippingAddress;
        this.state = state;
        this.isMainOrder = false;
    }

    public ArrayList<OrderComponent> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderComponent> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }

    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }

    public LocalDateTime getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(LocalDateTime placementDate) {
        this.placementDate = placementDate;
    }

    public LocalDateTime getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDateTime shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Boolean getMainOrder() {
        return isMainOrder;
    }

    public void setMainOrder(Boolean mainOrder) {
        isMainOrder = mainOrder;
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


        ArrayList<OrderComponent> sortedItems = new ArrayList<>();
        for (OrderComponent item : items) {
            if (item instanceof ProductItem) {
                sortedItems.add(item);
            }
        }
        for (OrderComponent item : items) {
            if (item instanceof Order) {
                sortedItems.add(item);
            }
        }

        // Print orders
        for (int i = 0; i < sortedItems.size(); i++) {
            info.append("\n ").append(sortedItems.get(i).getInformation());
            if (i != sortedItems.size() - 1) {
                info.append(", ");
            }
        }
        if (items.isEmpty())
            info.append("]}");

        return info.toString();
    }

    public Double getPrice() {
        double price = 0;
        for (OrderComponent item : items) {
            if (item instanceof ProductItem) {
                price += item.getPrice();
            }
        }
        return price;
    }

}

