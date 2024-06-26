package com.example.http;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class RMISearchServiceImpl implements RMISearchService {
    Log log = LogFactory.getLog(RMISearchServiceImpl.class);

    @Override
    public String search(String keyword) {
        log.debug("call service keyword "+ keyword);
        return keyword;
    }

}
