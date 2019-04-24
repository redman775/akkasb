package com.red.akkasb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void show(int myCounter) {
        logger.info("myCounter: " + myCounter);
    }
}