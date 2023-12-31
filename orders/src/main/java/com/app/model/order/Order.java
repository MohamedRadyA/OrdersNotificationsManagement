package com.app.model.order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Order implements OrderComponent {
    private ArrayList<OrderComponent> items;
    private Integer id;
    private String buyerUsername;
    private Date placementDate;
    private Date shippingDate;
    private String shippingAddress;
    private OrderState state;

    private Boolean isMainOrder;

    public Order(Integer id, String buyerUsername, Date placementDate, Date shippingDate, String shippingAddress, OrderState state) {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }

    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
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

    public Double getPrice() {
        Double price = 0.0;
        for (OrderComponent item : items) {
            if (item instanceof ProductItem) {
                price += item.getPrice();
            }
        }
        return price;
    }

}

