package com.app.notifications;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class ChannelFactory {
    private static final Map<String, Supplier<Channel>> channelRegistry = new HashMap<>();

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
