package com.app.model.channel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class ChannelFactory {
    private static final Map<String, Supplier<Channel>> channelRegistry = new HashMap<>();

    static {
        init();
    }
    public static void init() {
        registerChannel("email", EmailDecorator::new);
        registerChannel("sms", SMSDecorator::new);
    }

    public static void registerChannel(String channelType, Supplier<Channel> channelSupplier) {
        channelRegistry.put(channelType.toLowerCase(), channelSupplier);
    }

    public static Channel createChannel(String channelType) {
        Supplier<Channel> channelSupplier = channelRegistry.get(channelType.toLowerCase());
        if (channelSupplier != null) {
            return channelSupplier.get();
        }
        return null;
    }
}
