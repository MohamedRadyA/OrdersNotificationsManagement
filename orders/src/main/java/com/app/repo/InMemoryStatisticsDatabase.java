package com.app.repo;

import com.app.notifications.NotificationTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryStatisticsDatabase implements StatisticsDatabase {
    private Map<String, Integer> addressCounter;
    private Map<NotificationTemplate, Integer> templateCounter;

    public InMemoryStatisticsDatabase() {
        addressCounter = new HashMap<>();
        templateCounter = new HashMap<>();
    }

    public Map<String, Integer> getStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        String mostNotifiedAddress = null;
        NotificationTemplate mostSentTemplate = null;
        int mostNotifiedAddressCount = 0, mostSentTemplateCount = 0;

        for (Map.Entry<String, Integer> entry : addressCounter.entrySet()) {
            if (entry.getValue() > mostNotifiedAddressCount) {
                mostNotifiedAddressCount = entry.getValue();
                mostNotifiedAddress = entry.getKey();
            }
        }

        for (Map.Entry<NotificationTemplate, Integer> entry : templateCounter.entrySet()) {
            if (entry.getValue() > mostSentTemplateCount) {
                mostSentTemplateCount = entry.getValue();
                mostSentTemplate = entry.getKey();
            }
        }
        if (mostNotifiedAddress != null)
            statistics.put("mostNotifiedAddress: " + mostNotifiedAddress, mostNotifiedAddressCount);
        if (mostSentTemplate != null)
            statistics.put("mostSentTemplate: " + mostSentTemplate, mostSentTemplateCount);
        return statistics;
    }

    @Override
    public void incrementAddressCounter(String address) {
        addressCounter.putIfAbsent(address, 0);
        addressCounter.put(address, addressCounter.get(address) + 1);
    }

    @Override
    public void incrementTemplateCounter(NotificationTemplate template) {
        templateCounter.putIfAbsent(template, 0);
        templateCounter.put(template, templateCounter.get(template) + 1);
    }
}
