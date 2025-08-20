package org.ayd.apimechanicalworkshop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping("/")
    public String exampleEndpoint() {
        return "Hello, World!";
    }
}
