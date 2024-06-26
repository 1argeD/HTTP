package com.example.http;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class RMICallServiceImpl implements RMICallService{
    Log log = LogFactory.getLog(RMICallServiceImpl.class);

    private RMISearchService rmiSearchService;

    public  void setRmiSearchService(RMISearchService rmiSearchService) {
    this.rmiSearchService = rmiSearchService;
    }

    @Override
    public String call(String word) {
        return rmiSearchService.search(word);
    }
}
