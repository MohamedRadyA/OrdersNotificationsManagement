package com.app.notifications;

import java.util.HashMap;
import java.util.Map;

public enum NotificationTemplate {
    ORDER_PLACEMENT("Dear {name}, your order of the {product} has been placed. Thanks for using our store."),
    SUCCESSFUL_SHIPMENT("Dear {name}, your order of the {product} has been shipped. Thanks for using our store."),
    USER_CREATION("Dear {name}, your account has been created successfully. Welcome to our store."),
    ORDER_CANCELLATION("Dear {name}, your order of the {product} has been cancelled. We hope to serve you again."),
    SHIPMENT_CANCELLATION("Dear {name}, the shipment of your order of the {product} has been cancelled.");


    public String template;

    NotificationTemplate(String template) {
        this.template = template;
    }

    public String parseTemplate(HashMap<String, String> map) {
        String parsedTemplate = this.template;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            parsedTemplate = parsedTemplate.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return parsedTemplate;
    }
}
