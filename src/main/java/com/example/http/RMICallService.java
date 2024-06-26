package com.example.http;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public interface RMICallService {
    Log log = LogFactory.getLog(RMICallService.class);

    String call(String  word);
}
