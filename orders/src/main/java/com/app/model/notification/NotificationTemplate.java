package com.app.model.notification;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public enum NotificationTemplate {
    ORDER_PLACEMENT(Map.ofEntries(
            entry("en", "Dear {name}, your order with order id {id} has been placed. Thanks for using our store."),
            entry("ar", "عزيزي {name}، تم تقديم طلبك برقم {id}. شكرا لاستخدام متجرنا."),
            entry("fr", "Cher {name}, votre commande avec l'ID de commande {id} a été passée. Merci d'utiliser notre magasin."),
            entry("de", "Lieber {name}, Ihre Bestellung mit der Bestellnummer {id} wurde aufgegeben. Vielen Dank, dass Sie unseren Shop nutzen."),
            entry("es", "Estimado {name}, su pedido con el número de pedido {id} ha sido realizado. Gracias por usar nuestra tienda."))),

    ORDER_SHIPMENT(Map.ofEntries(
            entry("en","Dear {name}, your order with order id {id} has been shipped. Thanks for using our store."),
            entry("ar", "عزيزي {name}، تم شحن طلبك برقم {id}. شكرا لاستخدام متجرنا."),
            entry("fr", "Cher {name}, votre commande avec l'ID de commande {id} a été expédiée. Merci d'utiliser notre magasin."),
            entry("de", "Lieber {name}, Ihre Bestellung mit der Bestellnummer {id} wurde versendet. Vielen Dank, dass Sie unseren Shop nutzen."),
            entry("es", "Estimado {name}, su pedido con el número de pedido {id} ha sido enviado. Gracias por usar nuestra tienda."))),

    USER_CREATION(Map.ofEntries(
            entry("en","Dear {name}, your account has been created successfully. Welcome to our store."),
            entry("ar", "عزيزي {name}، تم إنشاء حسابك بنجاح. مرحبا بكم في متجرنا."),
            entry("fr", "Cher {name}, votre compte a été créé avec succès. Bienvenue dans notre magasin."),
            entry("de", "Lieber {name}, Ihr Konto wurde erfolgreich erstellt. Willkommen in unserem Shop."),
            entry("es", "Estimado {name}, su cuenta ha sido creada con éxito. Bienvenido a nuestra tienda."))),
    USER_LOGIN(Map.ofEntries(
            entry("en","Dear {name}, you have logged in successfully. Welcome to our store."),
            entry("ar", "عزيزي {name}، لقد قمت بتسجيل الدخول بنجاح. مرحبا بكم في متجرنا."),
            entry("fr", "Cher {name}, vous vous êtes connecté avec succès. Bienvenue dans notre magasin."),
            entry("de", "Lieber {name}, Sie haben sich erfolgreich angemeldet. Willkommen in unserem Shop."),
            entry("es", "Estimado {name}, ha iniciado sesión con éxito. Bienvenido a nuestra tienda.")));


    /**
     * Maps language to corresponding template
     */
    private Map<String, String> templates;

    NotificationTemplate(Map<String, String> templates) {
        this.templates = templates;
    }

    public String parseTemplate(Map<String, String> map) {
        String parsedTemplate = this.templates.get(map.get("lang"));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            parsedTemplate = parsedTemplate.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return parsedTemplate;
    }
}
