package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HelloWorldTest {
    private final HelloWorld helloWorld = new HelloWorld();

    @Test
    void getMessage_ReturnsHelloWorld() {
        assertEquals("Hello, World!", helloWorld.getMessage());
    }
}
