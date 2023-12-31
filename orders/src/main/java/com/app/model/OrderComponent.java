package com.app.model;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OrderComponent {
    boolean addChild (OrderComponent component);
    boolean removeChild (OrderComponent component);

    String getInformation ();
}
