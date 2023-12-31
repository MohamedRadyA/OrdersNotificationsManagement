package com.app.notifications;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public enum NotificationTemplate {
    ORDER_PLACEMENT(Map.ofEntries(
            entry("en", "Dear {name}, your order of the {product} has been placed. Thanks for using our store."),
            entry("ar", "عزيزي {name}، تم تقديم طلبك لـ {product}. شكرا لاستخدام متجرنا."),
            entry("fr", "Cher {name}, votre commande du {product} a été placée. Merci d'utiliser notre magasin."),
            entry("de", "Lieber {name}, Ihre Bestellung des {product} wurde aufgegeben. Vielen Dank, dass Sie unseren Shop nutzen."),
            entry("es", "Estimado {name}, su pedido del {product} ha sido realizado. Gracias por usar nuestra tienda."))),
    SUCCESSFUL_SHIPMENT(Map.ofEntries(
            entry("en","Dear {name}, your order of the {product} has been shipped. Thanks for using our store."),
            entry("ar", "عزيزي {name}، تم شحن طلبك لـ {product}. شكرا لاستخدام متجرنا."),
            entry("fr", "Cher {name}, votre commande du {product} a été expédiée. Merci d'utiliser notre magasin."),
            entry("de", "Lieber {name}, Ihre Bestellung des {product} wurde versandt. Vielen Dank, dass Sie unseren Shop nutzen."),
            entry("es", "Estimado {name}, su pedido del {product} ha sido enviado. Gracias por usar nuestra tienda."))),
    USER_CREATION(Map.ofEntries(
            entry("en","Dear {name}, your account has been created successfully. Welcome to our store."),
            entry("ar", "عزيزي {name}، تم إنشاء حسابك بنجاح. مرحبا بكم في متجرنا."),
            entry("fr", "Cher {name}, votre compte a été créé avec succès. Bienvenue dans notre magasin."),
            entry("de", "Lieber {name}, Ihr Konto wurde erfolgreich erstellt. Willkommen in unserem Shop."),
            entry("es", "Estimado {name}, su cuenta ha sido creada con éxito. Bienvenido a nuestra tienda."))),
    ORDER_CANCELLATION(Map.ofEntries(
            entry("en","Dear {name}, your order of the {product} has been cancelled. We hope to serve you again."),
            entry("ar", "عزيزي {name}، تم إلغاء طلبك لـ {product}. نأمل أن نخدمك مرة أخرى."),
            entry("fr", "Cher {name}, votre commande du {product} a été annulée. Nous espérons vous servir à nouveau."),
            entry("de", "Lieber {name}, Ihre Bestellung des {product} wurde storniert. Wir hoffen, Sie bald wieder zu bedienen."),
            entry("es", "Estimado {name}, su pedido del {product} ha sido cancelado. Esperamos volver a servirle."))),
    SHIPMENT_CANCELLATION(Map.ofEntries(
            entry("en","Dear {name}, the shipment of your order of the {product} has been cancelled."),
            entry("ar", "عزيزي {name}، تم إلغاء شحن طلبك لـ {product}."),
            entry("fr", "Cher {name}, l'expédition de votre commande du {product} a été annulée."),
            entry("de", "Lieber {name}, der Versand Ihrer Bestellung des {product} wurde storniert."),
            entry("es", "Estimado {name}, el envío de su pedido del {product} ha sido cancelado.")));


    /**
     * Maps language to corresponding template
     */
    private Map<String, String> templates;

    NotificationTemplate(Map<String, String> templates) {
        this.templates = templates;
    }

    public String parseTemplate(Map<String, Object> map) {
        String parsedTemplate = this.templates.get(map.get("lang"));
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            parsedTemplate = parsedTemplate.replace("{" + entry.getKey() + "}", entry.getValue().toString());
        }
        return parsedTemplate;
    }
}
