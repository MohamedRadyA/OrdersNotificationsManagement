package com.app.model.order;

public interface OrderComponent {
    boolean addChild (OrderComponent component);
    boolean removeChild (OrderComponent component);

    Double getPrice();
}
