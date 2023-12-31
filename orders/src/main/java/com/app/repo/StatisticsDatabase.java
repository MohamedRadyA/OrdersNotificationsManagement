package com.app.repo;


import com.app.notifications.NotificationTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface StatisticsDatabase {
    Map<String, Integer> getStatistics();

    void incrementAddressCounter(String address);

    void incrementTemplateCounter(NotificationTemplate template);

}
