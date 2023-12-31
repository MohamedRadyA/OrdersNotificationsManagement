package com.app.repo;


import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface StatisticsDatabase {
    Map<String, Integer> getStatistics();
}
