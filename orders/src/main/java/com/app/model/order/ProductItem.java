package com.app.model.order;

public class ProductItem implements OrderComponent {
    private String serialNumber;
    private Integer quantity;

    private final Double price;

    public ProductItem(String serialNumber, Integer quantity, Double price) {
        this.serialNumber = serialNumber;
        this.quantity = quantity;
        this.price = price;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean addChild(OrderComponent component) {
        throw new UnsupportedOperationException("Current operation is not support for this object");
    }

    @Override
    public boolean removeChild(OrderComponent component) {
        throw new UnsupportedOperationException("Current operation is not support for this object");
    }

    @Override
    public Double getPrice() {
        return price * quantity;
    }
}
