package com.app.repo;


import com.app.model.notification.NotificationTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface StatisticsDatabase {
    Map<String, Integer> getStatistics();

    void incrementAddressCounter(String address);

    void incrementTemplateCounter(NotificationTemplate template);

}
