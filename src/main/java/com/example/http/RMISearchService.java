package com.example.http;


import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public interface RMISearchService {
    Log log = LogFactory.getLog(RMISearchService.class);
    String search(String keyword);
}
