package com.app.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ProductItem implements OrderComponent {
    private String serialNumber;
    private int quantity;

    public ProductItem(String serialNumber, int quantity) {
        this.serialNumber = serialNumber;
        this.quantity = quantity;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public int getQuantity() {
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
    public String getInformation() {
        return "\"ProductItem\"= {" +
                "serialNumber= '" + getSerialNumber() + '\'' +
                ", quantity= " + getQuantity() +
                '}';
    }
}