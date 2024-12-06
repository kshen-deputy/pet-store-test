package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    public static void main(String[] args) {
        HelloWorld app = new HelloWorld();
        System.out.println(app.getMessage());
    }

    public String getMessage() {
        logger.info("Generating hello world message");
        return "Hello, World!";
    }
}
